using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Windows.Forms.DataVisualization.Charting;
using System.Windows.Forms.VisualStyles;
using TSB_OdooControl;

namespace TSB_OdooControl_
{
    public partial class Programa : Form
    {

        // Konexioa erabiltzeko objetuak sortu
        private MySQLConnection konexioaMySQL = new MySQLConnection();

        private DataTable originalDataTable;

        // Programaren hasiera funtzioa
        public Programa()
        {
            InitializeComponent();

            KonfiguratuCharts();

            datuakKargatuCHART();
        }
        
        // Programa kargatzerakoan zer egin behar duen
        private void Programa_Load(object sender, EventArgs e)
        {

        }

        // Logoaren irudiari klik egiterakoan web orria irekiko da.
        private void pictureBox1_Click(object sender, EventArgs e)
        {
            var ps = new ProcessStartInfo("https://tsbenpresa.github.io/")
            {
                UseShellExecute = true,
                Verb = "open"
            };
            Process.Start(ps);
        }

        // Itxi irudiaru klik egiterakoan app-a itxi
        private void pictureBox2_Click(object sender, EventArgs e)
        {
            // Leihoa itxi
            this.Close();
        }

        private void BTNhasiera_Click(object sender, EventArgs e)
        {
            // Datu taula visible ba dago, kendu
            if (DGVtaulak.Visible == true)
            {
                DGVtaulak.Visible = false;
            }

            // Bilaketa panel ixkutatu hasiera orrian
            if (panelBilaketa.Visible == true)
            {
                panelBilaketa.Visible = false;
            }

            // Irekita al dagoen edo ez konprobatzkeo
            if (panelGrafiko.Visible != true)
            {
                // Leihoaren titulua aldatu
                Label_Titulua.Text = "HASIERA";

                // Hasiera sartzerakoan kargatu behar duten elementuak
                panelGrafiko.Visible = true;

            }
            else
            {
                // Hasiera irekita badago, ez dugu ezer egingo
                MessageBox.Show("Leihoa irekita duzu");
            }


        }

        private void BTNproduktuak_Click(object sender, EventArgs e)
        {
            
            String selectSQL = "SELECT * FROM Productos";

            // Leihoaren titulua aldatu
            Label_Titulua.Text = "PRODUKTUAK";

            // Datu baseko datuak erabilita, kargatu datagridview gainean
            datubasekoDatuakLortu(selectSQL);

        }

        private void BTNsalmentak_Click(object sender, EventArgs e)
        {

            String selectSQL = "SELECT * FROM Compras";

            // Leihoaren titulua aldatu
            Label_Titulua.Text = "SALMENTAK";

            // Datu baseko datuak erabilita, kargatu datagridview gainean
            datubasekoDatuakLortu(selectSQL);

        }

        private void BTNgastuak_Click(object sender, EventArgs e)
        {

            String selectSQL = "SELECT * FROM Gastos";
            
            // Leihoaren titulua aldatu
            Label_Titulua.Text = "GASTUAK";

            // Datu baseko datuak erabilita, kargatu datagridview gainean
            datubasekoDatuakLortu(selectSQL);

        }

        private void BTNhornitzaileak_Click(object sender, EventArgs e)
        {
            String selectSQL = "SELECT * FROM Proveedores";

            // Leihoaren titulua aldatu
            Label_Titulua.Text = "HORNITZAILEAK";

            // Datu baseko datuak erabilita, kargatu datagridview gainean
            datubasekoDatuakLortu(selectSQL);

        }

        private void BTNerabiltzaileak_Click(object sender, EventArgs e)
        {
            String selectSQL = "SELECT * FROM Usuarios";

            // Leihoaren titulua aldatu
            Label_Titulua.Text = "ERABILTZAILEAK";

            // Datu baseko datuak erabilita, kargatu datagridview gainean
            datubasekoDatuakLortu(selectSQL);

        }

        private void datubasekoDatuakLortu(String selectSQL)
        {
            // Irekita al dagoen edo ez konprobatzkeo
            if (panelGrafiko.Visible == true)
            {
                // Beste leihoekin manejatzeko orduan, hasiera ixkutatuko dugu
                panelGrafiko.Visible = false;
            }

            // Taula ez badago bistan, bistaratu
            if (DGVtaulak.Visible != true)
            {
                DGVtaulak.Visible = true;
            }

            // Bilaketa panela visible egin
            if (panelBilaketa.Visible != true)
            {
                panelBilaketa.Visible = true;
            }

            // Datuak kargatu taularen barruan
            try
            {
                // Konexioa ireki
                konexioaMySQL.KonexioaIreki();

                // Datuak eskuratzeko erabiliko den adaptera sortu
                using (MySqlDataAdapter adapter = new MySqlDataAdapter(selectSQL, konexioaMySQL.getKonexioa()))
                {
                    // DataTable bat sortu datuak gordetzeko
                    DataTable dataTable = new DataTable();

                    // DataTable-a bete kontsulta emaitzekin
                    adapter.Fill(dataTable);

                    // DataTable-a asignatu DataGridView-en DataSource gisa
                    DGVtaulak.DataSource = dataTable;
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Ezin izan dugu datuak kargatu: " + ex.Message, "ARAZOA");
            }
            finally
            {
                // Konexioa itxi zihoan
                konexioaMySQL.KonexioaItxi();

                originalDataTable = ((DataTable)DGVtaulak.DataSource).Copy();

            }
        }

        private void bilatzailea_TB_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == (char)Keys.Enter)
            {
                // DatagridView-ko taula hartu, hurren gainean filtroak egiteko
                DataTable dt = (DataTable)DGVtaulak.DataSource;
                DataView dv = dt.DefaultView;

                // Produktu taulako kolumnak kontutan izanda, filtroak egin
                if (Label_Titulua.Text.Equals("PRODUKTUAK"))
                {
                    dv.RowFilter = $"izena LIKE '%{bilatzailea_TB.Text}%' OR kategoria LIKE '%{bilatzailea_TB.Text}%' OR mota LIKE '%{bilatzailea_TB.Text}%' OR prezioa LIKE '%{bilatzailea_TB.Text}%' OR pisua LIKE '%{bilatzailea_TB.Text}%' OR saldu_ok LIKE '%{bilatzailea_TB.Text}%' OR erosi_ok LIKE '%{bilatzailea_TB.Text}%' OR faktura_politika LIKE '%{bilatzailea_TB.Text}%' OR deskribapena LIKE '%{bilatzailea_TB.Text}%'";
                }

                if (Label_Titulua.Text.Equals("SALMENTAK"))
                {
                    dv.RowFilter = $"izena LIKE '%{bilatzailea_TB.Text}%' OR estatua LIKE '%{bilatzailea_TB.Text}%' OR faktura LIKE '%{bilatzailea_TB.Text}%' OR klientea LIKE '%{bilatzailea_TB.Text}%' OR enpresa LIKE '%{bilatzailea_TB.Text}%' OR prezio_base LIKE '%{bilatzailea_TB.Text}%' OR bez LIKE '%{bilatzailea_TB.Text}%' OR prezio_totala LIKE '%{bilatzailea_TB.Text}%' OR eskaera_data LIKE '%{bilatzailea_TB.Text}%' OR baimentze_data LIKE '%{bilatzailea_TB.Text}%'";
                }

                if (Label_Titulua.Text.Equals("GASTUAK"))
                {
                    dv.RowFilter = $"izena LIKE '%{bilatzailea_TB.Text}%' OR enpresa LIKE '%{bilatzailea_TB.Text}%' OR eroslea LIKE '%{bilatzailea_TB.Text}%' OR produktua LIKE '%{bilatzailea_TB.Text}%' OR ordaindu_modua LIKE '%{bilatzailea_TB.Text}%' OR estatua LIKE '%{bilatzailea_TB.Text}%' OR sortze_data LIKE '%{bilatzailea_TB.Text}%' OR ordaindu_data LIKE '%{bilatzailea_TB.Text}%' OR deskribapena LIKE '%{bilatzailea_TB.Text}%' OR ordaindu_prezioa LIKE '%{bilatzailea_TB.Text}%'";
                }

                if (Label_Titulua.Text.Equals("HORNITZAILEAK"))
                {
                    dv.RowFilter = $"izena LIKE '%{bilatzailea_TB.Text}%' OR herria LIKE '%{bilatzailea_TB.Text}%' OR mota LIKE '%{bilatzailea_TB.Text}%' OR korreoa LIKE '%{bilatzailea_TB.Text}%' OR mugikorra LIKE '%{bilatzailea_TB.Text}%' OR komentarioa LIKE '%{bilatzailea_TB.Text}%'";
                }

                if (Label_Titulua.Text.Equals("ERABILTZAILEAK"))
                {
                    dv.RowFilter = $"erabiltzailea LIKE '%{bilatzailea_TB.Text}%' OR email LIKE '%{bilatzailea_TB.Text}%' OR enpresa LIKE '%{bilatzailea_TB.Text}%'";
                }

                // Egindako bilaketaren filtroko datuak bistaratu
                DGVtaulak.DataSource = dv.ToTable();

                // Bilaketa egiteko textbox-a utzik ba dago, filtroak reseteatu, hasiera datuak irakusteko
                if (string.IsNullOrEmpty(bilatzailea_TB.Text))
                {
                    // Lehenengo taula kargatu
                    DGVtaulak.DataSource = originalDataTable.Copy();
                }
            }
        }

        private void resetTaulak_Click(object sender, EventArgs e)
        {
            // Lehenengo taula kargatu
            DGVtaulak.DataSource = originalDataTable.Copy();

            // Bilaketa garbitu
            bilatzailea_TB.Clear();
        }

        private void KonfiguratuCharts()
        {
            // Grafikak konfiguratu
            KonfiguratuChart(Chart_Irabaziak, "Irabaziak", SeriesChartType.Column);
            KonfiguratuChart(Chart_hornitzaileak, "Hornitzaileak", SeriesChartType.Kagi);
        }

        private void KonfiguratuChart(Chart chart, string serieIzena, SeriesChartType mota)
        {
            if (serieIzena.Equals("Irabaziak"))
            {
                // Irabaziak grafikoko datuak mugitu
                chart.Series.Add(serieIzena);
                Chart_Irabaziak.Series.Remove(Chart_Irabaziak.Series["Series1"]);
                chart.Series[serieIzena].ChartType = SeriesChartType.Line;
                chart.Series[serieIzena].BorderWidth = 7;
                chart.Series[serieIzena].XValueType = ChartValueType.Date;
                chart.ChartAreas["ChartArea1"].AxisX.LabelStyle.Format = "MM-dd";

                // Puntuak konfiguratu tokatzen den tokian
                Chart_Irabaziak.Series["Irabaziak"].MarkerStyle = MarkerStyle.Circle;
                Chart_Irabaziak.Series["Irabaziak"].MarkerSize = 10;
                Chart_Irabaziak.Series["Irabaziak"].MarkerColor = Color.Black;

            }
            else if (serieIzena.Equals("Hornitzaileak"))
            {
                // Hornitzaileak grafikoko datuak mugitu
                Chart_hornitzaileak.Series.Clear();
                Chart_hornitzaileak.ChartAreas.Clear();

                Chart_hornitzaileak.BackColor = Color.Transparent;

                ChartArea chartArea = Chart_hornitzaileak.ChartAreas.Add("ChartArea1");
                chartArea.BackColor = Color.Transparent;

                Chart_hornitzaileak.Series.Add("Hornitzaileak");
                Chart_hornitzaileak.Series["Hornitzaileak"].ChartType = SeriesChartType.Pie;
                Chart_hornitzaileak.Series["Hornitzaileak"].XValueType = ChartValueType.String;
                Chart_hornitzaileak.Series["Hornitzaileak"].YValueType = ChartValueType.Double;
                Chart_hornitzaileak.ChartAreas["ChartArea1"].AxisX.LabelStyle.Enabled = false;
            }

        }

        private void datuakKargatuCHART()
        {
            // Salmenta datuak kargatu grafikoaren barruan
            salmentakGrafikoaKargatu();

            // Gehien erosi duen hornitzailearen datuak kargatu
            hornitzaileGrafikoaKargatu();

            // Gehien saldutako produktuak
            produktuGrafikoa();

            // Azpiko datu txikiak kargatu
            enpresaDatuakKargatu();
        }

        private void hornitzaileGrafikoaKargatu()
        {
            try
            {
                // Konexioa ireki
                konexioaMySQL.KonexioaIreki();

                // Datuak lortzeko kontsulta
                string consulta = "SELECT klientea, COUNT(*) as TotalCompras FROM compras GROUP BY klientea ORDER BY TotalCompras DESC LIMIT 5";
                MySqlCommand cmd = new MySqlCommand(consulta, konexioaMySQL.getKonexioa());

                using (MySqlDataReader reader = cmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        string hornitzailea = reader.GetString("klientea");
                        int salmentak = reader.GetInt32("TotalCompras");

                        // Agregar datos al gráfico
                        Chart_hornitzaileak.Series["Hornitzaileak"].Points.AddXY(hornitzailea, salmentak);
                    }
                }
            }
            catch (Exception)
            {
                MessageBox.Show("Ezin izan ditugu datuak kargatu Hornitzaile grafikoaren barruan.", "Hornitzailea grafikoa");
                throw;
            }
        }

        private void salmentakGrafikoaKargatu()
        {
            try
            {
                // Konexioa ireki
                konexioaMySQL.KonexioaIreki();

                // Irabazien kontsulta
                string irabaziKontsulta = "SELECT eskaera_data, prezio_totala FROM compras WHERE prezio_totala > 0";
                MySqlCommand cmdIrabazi = new MySqlCommand(irabaziKontsulta, konexioaMySQL.getKonexioa());

                // Galderen kontsulta
                string galderaKontsulta = "SELECT sortze_data AS eskaera_data, -ordaindu_prezioa AS prezio_totala FROM gastos";
                MySqlCommand cmdGaldera = new MySqlCommand(galderaKontsulta, konexioaMySQL.getKonexioa());

                // Irabazi datuak jaso
                List<Datuak> irabaziak = DatuakLortu(cmdIrabazi);

                // Galdera datuak jaso
                List<Datuak> galderak = DatuakLortu(cmdGaldera);

                // Irabazi eta galderen datuak elkartu
                List<Datuak> guztiak = irabaziak.Concat(galderak).ToList();

                // Dena ordenatu dataren arabera
                guztiak.Sort((a, b) => DateTime.Compare(a.Fecha, b.Fecha));

                // Irabaziak chartean sartu
                foreach (var datua in guztiak)
                {
                    Chart_Irabaziak.Series["Irabaziak"].Points.AddXY(datua.Fecha, datua.GananciaTotal);
                }
            }
            catch (Exception)
            {
                MessageBox.Show("Ezin izan dugu konexioa ondo ireki.");
                throw;
            }


        }

        private void produktuGrafikoa()
        {
            try
            {
                // Konexioa ireki
                konexioaMySQL.KonexioaIreki();

                // Datuak lortu ahal izateko kontsulta
                string consulta = "SELECT izena, COUNT(izena) as kopurua FROM compras GROUP BY izena ORDER BY kopurua DESC LIMIT 5";
                MySqlCommand cmd = new MySqlCommand(consulta, konexioaMySQL.getKonexioa());

                // Grafikoak gehitu aurretik garbitu
                panelGrafikoak.Controls.Clear();

                using (MySqlDataReader reader = cmd.ExecuteReader())
                {
                    int fila = 0;

                    while (reader.Read() && fila < 5)
                    {
                        string produktuIzena = reader.GetString("izena");
                        int kantitatea = reader.GetInt32("kopurua");

                        // Grafikoak sortu
                        Chart Chart_Produktuak = new Chart();
                        Chart_Produktuak.Size = new Size(200, 300);
                        Chart_Produktuak.ChartAreas.Add("ChartArea");
                        Chart_Produktuak.Series.Add("Salmentak");
                        Chart_Produktuak.Series["Salmentak"].ChartType = SeriesChartType.Bar;
                        Chart_Produktuak.Dock = DockStyle.Fill;
                        Chart_Produktuak.BackColor = Color.Transparent;
                        Chart_Produktuak.ForeColor = Color.Black;

                        // Grafikoko datuak kargatu
                        Chart_Produktuak.Series["Salmentak"].Points.AddXY(produktuIzena, kantitatea);

                        // Grafiko bistaratu pantailan irakutsi
                        panelGrafikoak.Controls.Add(Chart_Produktuak, 0, fila);

                        fila++;
                    }
                }
            }
            catch (Exception)
            {
                MessageBox.Show("Ezin izan dugu konexioa ondo ireki.");
                throw;
            }
        }

        // Beheko aldeak azaltzen diren laukien datuak kargatzeko
        private void enpresaDatuakKargatu()
        {
            // Salmenta totalak kargatzeko
            salmentaTotalakKargatu();

            // Hornitzaile kantitatea Kargatzeko
            hornitzaileaKantitateaKargatu();

            // Produktuen kantitatea Kargatzeko
            produktuKantitateaKargatu();

            // Irabazitako diruaren kalkulua egin eta kargatzeko
            irabaziaKantitateaKargatu();

            // Langileak irakusteko
            langileakKantitatea();

            // Gastuak kalkulatzeko
            gastuakKantitatea();

        }
        private void salmentaTotalakKargatu()
        {
            try
            {
                // Konexioa ireki
                konexioaMySQL.KonexioaIreki();

                // prezio_totala kolumnaren zenbakia lortzeko kontsulta
                string kontsultaSQL = "SELECT SUM(prezio_totala) FROM tsb_db.compras";
                MySqlCommand cmd = new MySqlCommand(kontsultaSQL, konexioaMySQL.getKonexioa());

                // Kontsulta exekutatu eta emaitza lortu
                object emaitza = cmd.ExecuteScalar();

                // Emaitza ez bada null, labelSuma etiketan erakutsi
                if (emaitza != null)
                {
                    // Etiketan zenbakia erakutsi
                    label_Irabaziak.Text = "Nº: " + emaitza.ToString() + "€";
                }
                else
                {
                    // Emaitza null bada, mezua erakutsi edo behar bada kudeatu
                    label_Irabaziak.Text = "null";
                }
            }
            catch (Exception)
            {
                MessageBox.Show("Ezin izan dugu konexioa ondo ireki.");
                throw;
            }

        }
        private void hornitzaileaKantitateaKargatu()
        {
            try
            {
                // Konexioa ireki
                konexioaMySQL.KonexioaIreki();

                // hornitzaile kantitate totala jakitzeko
                string kontsultaSQL = "SELECT count(*) FROM tsb_db.proveedores;";
                MySqlCommand cmd = new MySqlCommand(kontsultaSQL, konexioaMySQL.getKonexioa());

                // Kontsulta exekutatu eta emaitza lortu
                object emaitza = cmd.ExecuteScalar();

                // Emaitza ez bada null, labelSuma etiketan erakutsi
                if (emaitza != null)
                {
                    // Etiketan zenbakia erakutsi
                    label_Hornitzaileak.Text = "Nº: " + emaitza.ToString();
                }
                else
                {
                    // Emaitza null bada, mezua erakutsi edo behar bada kudeatu
                    label_Hornitzaileak.Text = "null";
                }
            }
            catch (Exception)
            {
                MessageBox.Show("Ezin izan dugu konexioa ondo ireki.");
                throw;
            }
        }
        private void produktuKantitateaKargatu()
        {
            try
            {
                // Konexioa ireki
                konexioaMySQL.KonexioaIreki();

                // hornitzaile kantitate totala jakitzeko
                string kontsultaSQL = "SELECT count(*) FROM tsb_db.productos;";
                MySqlCommand cmd = new MySqlCommand(kontsultaSQL, konexioaMySQL.getKonexioa());

                // Kontsulta exekutatu eta emaitza lortu
                object emaitza = cmd.ExecuteScalar();

                // Emaitza ez bada null, labelSuma etiketan erakutsi
                if (emaitza != null)
                {
                    // Etiketan zenbakia erakutsi
                    label_Produktuak.Text = "Nº: " + emaitza.ToString();
                }
                else
                {
                    // Emaitza null bada, mezua erakutsi edo behar bada kudeatu
                    label_Produktuak.Text = "null";
                }
            }
            catch (Exception)
            {
                MessageBox.Show("Ezin izan dugu konexioa ondo ireki.");
                throw;
            }

        }
        private void irabaziaKantitateaKargatu()
        {
            // SQL kontsultak zehaztu
            string irabaziakSQL = "SELECT SUM(prezio_totala) FROM tsb_db.compras;";
            string gastuakSQL = "SELECT SUM(ordaindu_prezioa) FROM tsb_db.gastos WHERE estatua = 'done';";

            try
            {
                // Konexioa ireki
                konexioaMySQL.KonexioaIreki();

                // Komandoa exekutatzeko
                MySqlCommand cmdIrabaziak = new MySqlCommand(irabaziakSQL, konexioaMySQL.getKonexioa());
                MySqlCommand cmdGastuak = new MySqlCommand(gastuakSQL, konexioaMySQL.getKonexioa());

                // Irabaziak exekutatu
                object irabaziakEmaitza = cmdIrabaziak.ExecuteScalar();
                string irabaziakTotala = irabaziakEmaitza != null ? irabaziakEmaitza.ToString() : "0";

                // Gastuak exekutatu
                object gastuakEmaitza = cmdGastuak.ExecuteScalar();
                string gastuakTotala = gastuakEmaitza != null ? gastuakEmaitza.ToString() : "0";

                // Konexioa itxi
                konexioaMySQL.KonexioaItxi();

                // Emaitzak erakutsi
                int zbk_Kalkulua;

                // Kalkulua: Irabaziak - Gastuak
                try
                {
                    // IrabaziakTotala eta GastuakTotala String-etik Integer-era bihurtu
                    int irabaziak = Convert.ToInt32(irabaziakTotala);
                    int gastuak = Convert.ToInt32(gastuakTotala);

                    // Kalkulua egin
                    zbk_Kalkulua = irabaziak - gastuak;

                    // Kalkuluaren emaitza erakutsi
                    label_Salmentak.Text = "Nº: " + zbk_Kalkulua.ToString() + "€";
                }
                catch (FormatException ex)
                {
                    // Konbertsioan errorea
                    MessageBox.Show($"Errorea konbertsioan: {ex.Message}", "Errorea");
                }

            }
            catch (Exception)
            {
                MessageBox.Show("Konexioarekin arazoak izan ditugu.", "Datubaseko konexioa");
                throw;
            }

        }
        private void langileakKantitatea()
        {

            // Langileak lortzeko kontsulta
            String langileakSQL = "SELECT COUNT(*) FROM tsb_db.usuarios;";

            try
            {
                // Konexioa ireki
                konexioaMySQL.KonexioaIreki();

                // Komandoa exekutatzeko
                MySqlCommand cmd = new MySqlCommand(langileakSQL, konexioaMySQL.getKonexioa());

                // Langileak exekutatu
                object langileakEmaitza = cmd.ExecuteScalar();
                string langileakTotala = langileakEmaitza != null ? langileakEmaitza.ToString() : "0";

                // Konexioa itxi
                konexioaMySQL.KonexioaItxi();

                // Emaitzak erakutsi
                label_Langileak.Text = "Nº: " + langileakTotala.ToString();

            }
            catch (Exception)
            {
                MessageBox.Show("Konexioarekin arazoak izan ditugu.", "Datubaseko konexioa");
                throw;
            }
        }
        private void gastuakKantitatea()
        {
            // Komprak
            string gastuakSQL = "SELECT SUM(ordaindu_prezioa) FROM tsb_db.gastos WHERE estatua = 'done';";

            try
            {
                // Konexioa ireki
                konexioaMySQL.KonexioaIreki();

                // Komandoa exekutatzeko
                MySqlCommand cmd = new MySqlCommand(gastuakSQL, konexioaMySQL.getKonexioa());

                // Langileak exekutatu
                object gastuakEmaitza = cmd.ExecuteScalar();
                string gastuakTotala = gastuakEmaitza != null ? gastuakEmaitza.ToString() : "0";

                // Konexioa itxi
                konexioaMySQL.KonexioaItxi();

                // Emaitzak erakutsi
                label_Gastuak.Text = "Nº: " + gastuakTotala.ToString() + "€";

            }
            catch (Exception)
            {
                MessageBox.Show("Konexioarekin arazoak izan ditugu.", "Datubaseko konexioa");
                throw;
            }



        }


        // Datuak gordetzeko
        public class Datuak
        {
            public DateTime Fecha { get; set; }
            public decimal GananciaTotal { get; set; }
        }

        private List<Datuak> DatuakLortu(MySqlCommand cmd)
        {
            using (MySqlDataReader reader = cmd.ExecuteReader())
            {
                List<Datuak> datos = new List<Datuak>();

                while (reader.Read())
                {
                    DateTime fecha = reader.GetDateTime("eskaera_data");
                    decimal gananciaTotal = reader.GetDecimal("prezio_totala");

                    datos.Add(new Datuak { Fecha = fecha, GananciaTotal = gananciaTotal });
                }

                datos.Sort((a, b) => DateTime.Compare(a.Fecha, b.Fecha));

                return datos;
            }
        }
    }
}
