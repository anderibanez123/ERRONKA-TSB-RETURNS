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

namespace TSB_OdooControl_
{
    public partial class Programa : Form
    {

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

        // Hurrengo asuntue




    }
}
