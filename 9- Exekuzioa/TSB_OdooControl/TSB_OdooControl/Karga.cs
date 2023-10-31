using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Reflection;
using System.IO;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TSB_OdooControl
{
    public partial class karga : Form
    {
        public karga()
        {
            InitializeComponent();
        }

        private void karga_Load(object sender, EventArgs e)
        {
            Assembly assembly = Assembly.GetExecutingAssembly();
            Stream videoStream = assembly.GetManifestResourceStream("TSB_OdooControl.Properties.Resources.karga.mp4");

            if (videoStream != null)
            {
                vlcControl1.SetMedia(videoStream);
                vlcControl1.Play();
            }
        }
    }
}
