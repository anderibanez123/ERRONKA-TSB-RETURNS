using Org.BouncyCastle.Tls;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using TSB_OdooControl;

namespace TSB_OdooControl_
{
    public partial class Hasiera : Form
    {

        private PostgreSQLConnection konexioa = new PostgreSQLConnection();

        public Hasiera()
        {
            InitializeComponent();
        }

        private void sartu_BT_Click(object sender, EventArgs e)
        {
            try
            {
                // Konexioa ireki
                konexioa.konexioaIreki();

                // Erabiltzailea konprobatu
                bool ondo = erabiltzaileaKonprobatu();

                if (ondo == true)
                {
                    // Abrir siguiente pestaña
                }

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
            if (erabiltzailea.Equals("TSB") && pasahitza.Equals("TSB"))
            {
                // Karga bideua hasi
                carga.Visible = true;

                // Kargatu beharrezko datuak kargatu
                DatuBaseDatuakKARGATU();

                // Karga bideua bukatu
                carga.Visible = false;

                // Ondo irten dela bueltatu
                return true;
            }
            else
            {
                MessageBox.Show("Sartutako erabiltzaile/pasahitza ez da zuzena.", "ERABILTZAILEA EDO PASAHITZ EZ ZUZENAK");
            }

            return false;
        }

        // Kargarako pantaila
        private void DatuBaseDatuakKARGATU()
        {

        }

        private void vlcControl1_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Datu basea kargatzen...");
        }
    }
}
