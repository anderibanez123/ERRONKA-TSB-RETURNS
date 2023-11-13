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

            datuakKargatuCHART_Irabaziak();
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
                chart.Series[serieIzena].ChartType = mota;
                chart.Series[serieIzena].XValueType = ChartValueType.Date;
                chart.ChartAreas["ChartArea1"].AxisX.LabelStyle.Format = "MM-dd";

            }else if (serieIzena.Equals("Hornitzaileak"))
            {
                // Hornitzaileak grafikoko datuak mugitu
                Chart_hornitzaileak.Series.Clear();
                Chart_hornitzaileak.ChartAreas.Clear();

                Chart_hornitzaileak.BackColor = Color.Transparent; // Establecer el fondo del gráfico como transparente

                ChartArea chartArea = Chart_hornitzaileak.ChartAreas.Add("ChartArea1");
                chartArea.BackColor = Color.Transparent;

                Chart_hornitzaileak.Series.Add("Hornitzaileak");
                Chart_hornitzaileak.Series["Hornitzaileak"].ChartType = SeriesChartType.Doughnut;
                Chart_hornitzaileak.Series["Hornitzaileak"].XValueType = ChartValueType.String;
                Chart_hornitzaileak.Series["Hornitzaileak"].YValueType = ChartValueType.Double;
                Chart_hornitzaileak.ChartAreas["ChartArea1"].AxisX.LabelStyle.Enabled = false;
            }

        }

        private void datuakKargatuCHART_Irabaziak()
        {
            // Salmenta datuak kargatu grafikoaren barruan
            salmentakGrafikoaKargatu();

            // Gehien erosi duen hornitzailearen datuak kargatu
            hornitzaileGrafikoaKargatu();
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

                // Kontsulta
                string consulta = "SELECT eskaera_data, prezio_totala FROM compras";
                MySqlCommand cmd = new MySqlCommand(consulta, konexioaMySQL.getKonexioa());

                using (MySqlDataReader reader = cmd.ExecuteReader())
                {
                    // Datuak gorde
                    List<Datos> datos = new List<Datos>();

                    // Datuak prestatu
                    while (reader.Read())
                    {
                        DateTime fecha = reader.GetDateTime("eskaera_data");
                        decimal gananciaTotal = reader.GetDecimal("prezio_totala");

                        datos.Add(new Datos { Fecha = fecha, GananciaTotal = gananciaTotal });
                    }

                    // Datuak dataren arabera txukundu
                    datos.Sort((a, b) => DateTime.Compare(a.Fecha, b.Fecha));

                    // Puntuak chart barruan sartu
                    foreach (var dato in datos)
                    {
                        Chart_Irabaziak.Series["Irabaziak"].Points.AddXY(dato.Fecha, dato.GananciaTotal);
                    }

                }
            }
            catch (Exception)
            {
                MessageBox.Show("Ezin izan dugu konexioa ondo ireki.");
                throw;
            }
        }

        // Datuak gordetzeko
        public class Datos
        {
            public DateTime Fecha { get; set; }
            public decimal GananciaTotal { get; set; }
        }


    }
}
