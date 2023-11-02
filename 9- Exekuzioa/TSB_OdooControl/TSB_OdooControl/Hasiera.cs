using System.Drawing.Drawing2D;

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

                MessageBox.Show("kaixo");
            }
            catch (Exception)
            {
                MessageBox.Show("Konexioarekin arazoa izan dugu.");
                throw;
            }
        }
    }
}