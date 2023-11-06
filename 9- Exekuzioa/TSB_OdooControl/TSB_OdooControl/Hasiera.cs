using Org.BouncyCastle.Tls;
using System.Drawing.Drawing2D;
using Vlc.DotNet.Core;
using Vlc.DotNet.Forms;

namespace TSB_OdooControl
{
    public partial class Hasiera : Form
    {
        private PostgreSQLConnection konexioa = new PostgreSQLConnection();

        public Hasiera()
        {
            InitializeComponent();
        }

        private void pictureBox1_Click(object sender, EventArgs e)
        {

        }

        private void Sartu_BT_Click(object sender, EventArgs e)
        {
            try
            {
                // Konexioa ireki
                konexioa.konexioaIreki();

                // Datu basea kargatu bitarteko bideoa
                bideoKarga();

                // Erabiltzailea konprobatu
                erabiltzaileaKonprobatu();

            }
            catch (Exception)
            {
                // Konexioarekin arazoa izaten ba dugu
                MessageBox.Show("Konexioarekin arazoa izan dugu.");
                throw;
            }
        }

        // Erabiltzailea konprobatzeko funtzioa
        private Boolean erabiltzaileaKonprobatu()
        {
            // Erabiltzailea eta pasahitza lortu
            String erabiltzailea = user_TB.Text.ToUpper().ToString();
            String pasahitza = pw_TB.Text.ToUpper().ToString();

            // String erabiltzaile taula lortzeko



            return false;
        }

        // Kargarako pantaila
        private void bideoKarga()
        {



        }

    }
}