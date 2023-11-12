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
            // Leihoaren titulua aldatu
            Label_Titulua.Text = "HASIERA";
        }

        private void BTNproduktuak_Click(object sender, EventArgs e)
        {
            
            String selectSQL = "SELECT * FROM Productos";

            // Leihoaren titulua aldatu
            Label_Titulua.Text = "PRODUKTUAK";

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

        private void BTNsalmentak_Click(object sender, EventArgs e)
        {

            String selectSQL = "SELECT * FROM Compras";

            // Leihoaren titulua aldatu
            Label_Titulua.Text = "SALMENTAK";

        }

        private void BTNgastuak_Click(object sender, EventArgs e)
        {

            String selectSQL = "SELECT * FROM Gastos";
            
            // Leihoaren titulua aldatu
            Label_Titulua.Text = "GASTUAK";

        }

        private void BTNhornitzaileak_Click(object sender, EventArgs e)
        {
            String selectSQL = "SELECT * FROM Proveedores";

            // Leihoaren titulua aldatu
            Label_Titulua.Text = "HORNITZAILEAK";

        }


        private void BTNerabiltzaileak_Click(object sender, EventArgs e)
        {
            String selectSQL = "SELECT * FROM Usuarios";

            // Leihoaren titulua aldatu
            Label_Titulua.Text = "ERABILTZAILEAK";

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









        // Hurrengo asuntue




    }
}
