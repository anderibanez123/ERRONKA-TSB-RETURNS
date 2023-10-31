namespace TSB_OdooControl
{
    partial class karga
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            saveFileDialog1 = new SaveFileDialog();
            vlcControl1 = new Vlc.DotNet.Forms.VlcControl();
            ((System.ComponentModel.ISupportInitialize)vlcControl1).BeginInit();
            SuspendLayout();
            // 
            // vlcControl1
            // 
            vlcControl1.BackColor = Color.Black;
            vlcControl1.Location = new Point(119, 82);
            vlcControl1.Name = "vlcControl1";
            vlcControl1.Size = new Size(551, 268);
            vlcControl1.Spu = -1;
            vlcControl1.TabIndex = 0;
            vlcControl1.Text = "vlcControl1";
            vlcControl1.VlcLibDirectory = null;
            vlcControl1.VlcMediaplayerOptions = null;
            // 
            // karga
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(800, 450);
            Controls.Add(vlcControl1);
            Name = "karga";
            Text = "KARGA PANTAILA";
            Load += karga_Load;
            ((System.ComponentModel.ISupportInitialize)vlcControl1).EndInit();
            ResumeLayout(false);
        }

        #endregion

        private SaveFileDialog saveFileDialog1;
        private Vlc.DotNet.Forms.VlcControl vlcControl1;
    }
}