namespace TSB_OdooControl
{
    partial class Hasiera
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
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
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Hasiera));
            logoPB = new PictureBox();
            Sartu_BT = new Button();
            label1 = new Label();
            user_TB = new TextBox();
            pw_TB = new TextBox();
            user_PB = new PictureBox();
            pass_PB = new PictureBox();
            barra1 = new PictureBox();
            barra2 = new PictureBox();
            ((System.ComponentModel.ISupportInitialize)logoPB).BeginInit();
            ((System.ComponentModel.ISupportInitialize)user_PB).BeginInit();
            ((System.ComponentModel.ISupportInitialize)pass_PB).BeginInit();
            ((System.ComponentModel.ISupportInitialize)barra1).BeginInit();
            ((System.ComponentModel.ISupportInitialize)barra2).BeginInit();
            SuspendLayout();
            // 
            // logoPB
            // 
            logoPB.Anchor = AnchorStyles.Top | AnchorStyles.Bottom | AnchorStyles.Left | AnchorStyles.Right;
            logoPB.BackColor = Color.Transparent;
            logoPB.Image = Properties.Resources.LOGO_DIFERENTE_BLANCO;
            logoPB.Location = new Point(397, 457);
            logoPB.Name = "logoPB";
            logoPB.Size = new Size(209, 122);
            logoPB.SizeMode = PictureBoxSizeMode.Zoom;
            logoPB.TabIndex = 0;
            logoPB.TabStop = false;
            // 
            // Sartu_BT
            // 
            Sartu_BT.Anchor = AnchorStyles.Top | AnchorStyles.Bottom | AnchorStyles.Left | AnchorStyles.Right;
            Sartu_BT.BackColor = Color.FromArgb(0, 119, 139);
            Sartu_BT.FlatAppearance.BorderColor = Color.FromArgb(0, 119, 139);
            Sartu_BT.FlatAppearance.BorderSize = 0;
            Sartu_BT.FlatAppearance.MouseDownBackColor = Color.FromArgb(0, 119, 139);
            Sartu_BT.FlatAppearance.MouseOverBackColor = Color.FromArgb(91, 164, 182);
            Sartu_BT.FlatStyle = FlatStyle.Flat;
            Sartu_BT.Font = new Font("Poppins Medium", 12F, FontStyle.Bold, GraphicsUnit.Point);
            Sartu_BT.ForeColor = Color.White;
            Sartu_BT.Location = new Point(364, 362);
            Sartu_BT.Name = "Sartu_BT";
            Sartu_BT.Size = new Size(280, 41);
            Sartu_BT.TabIndex = 1;
            Sartu_BT.Text = "SARTU";
            Sartu_BT.UseVisualStyleBackColor = false;
            // 
            // label1
            // 
            label1.Anchor = AnchorStyles.Top | AnchorStyles.Bottom | AnchorStyles.Left | AnchorStyles.Right;
            label1.AutoSize = true;
            label1.BackColor = Color.Transparent;
            label1.Font = new Font("Poppins", 19.8000011F, FontStyle.Bold, GraphicsUnit.Point);
            label1.ForeColor = Color.White;
            label1.Location = new Point(387, 27);
            label1.Name = "label1";
            label1.Size = new Size(253, 60);
            label1.TabIndex = 2;
            label1.Text = "ONGI ETORRI!";
            // 
            // user_TB
            // 
            user_TB.Anchor = AnchorStyles.Top | AnchorStyles.Bottom | AnchorStyles.Left | AnchorStyles.Right;
            user_TB.BackColor = Color.White;
            user_TB.BorderStyle = BorderStyle.None;
            user_TB.Font = new Font("Poppins", 12F, FontStyle.Regular, GraphicsUnit.Point);
            user_TB.ForeColor = SystemColors.WindowText;
            user_TB.Location = new Point(364, 175);
            user_TB.Name = "user_TB";
            user_TB.Size = new Size(334, 30);
            user_TB.TabIndex = 3;
            // 
            // pw_TB
            // 
            pw_TB.Anchor = AnchorStyles.Top | AnchorStyles.Bottom | AnchorStyles.Left | AnchorStyles.Right;
            pw_TB.BackColor = Color.White;
            pw_TB.BorderStyle = BorderStyle.None;
            pw_TB.Font = new Font("Poppins", 12F, FontStyle.Regular, GraphicsUnit.Point);
            pw_TB.ForeColor = SystemColors.WindowText;
            pw_TB.Location = new Point(364, 268);
            pw_TB.Name = "pw_TB";
            pw_TB.Size = new Size(334, 30);
            pw_TB.TabIndex = 4;
            // 
            // user_PB
            // 
            user_PB.Anchor = AnchorStyles.Top | AnchorStyles.Bottom | AnchorStyles.Left | AnchorStyles.Right;
            user_PB.BackColor = Color.Transparent;
            user_PB.Image = Properties.Resources.userIcon;
            user_PB.Location = new Point(304, 175);
            user_PB.Name = "user_PB";
            user_PB.Size = new Size(54, 37);
            user_PB.SizeMode = PictureBoxSizeMode.Zoom;
            user_PB.TabIndex = 5;
            user_PB.TabStop = false;
            user_PB.Click += pictureBox1_Click;
            // 
            // pass_PB
            // 
            pass_PB.Anchor = AnchorStyles.Top | AnchorStyles.Bottom | AnchorStyles.Left | AnchorStyles.Right;
            pass_PB.BackColor = Color.Transparent;
            pass_PB.Image = Properties.Resources.pwIcon;
            pass_PB.Location = new Point(304, 268);
            pass_PB.Name = "pass_PB";
            pass_PB.Size = new Size(54, 37);
            pass_PB.SizeMode = PictureBoxSizeMode.Zoom;
            pass_PB.TabIndex = 6;
            pass_PB.TabStop = false;
            // 
            // barra1
            // 
            barra1.BackColor = Color.Transparent;
            barra1.Image = Properties.Resources.Raya;
            barra1.Location = new Point(364, 205);
            barra1.Name = "barra1";
            barra1.Size = new Size(334, 2);
            barra1.SizeMode = PictureBoxSizeMode.StretchImage;
            barra1.TabIndex = 7;
            barra1.TabStop = false;
            // 
            // barra2
            // 
            barra2.BackColor = Color.Transparent;
            barra2.Image = Properties.Resources.Raya;
            barra2.Location = new Point(364, 303);
            barra2.Name = "barra2";
            barra2.Size = new Size(334, 2);
            barra2.SizeMode = PictureBoxSizeMode.StretchImage;
            barra2.TabIndex = 8;
            barra2.TabStop = false;
            // 
            // Hasiera
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            BackgroundImage = Properties.Resources.FondoVisualApp4;
            BackgroundImageLayout = ImageLayout.Stretch;
            ClientSize = new Size(1007, 566);
            Controls.Add(barra2);
            Controls.Add(barra1);
            Controls.Add(pass_PB);
            Controls.Add(user_PB);
            Controls.Add(pw_TB);
            Controls.Add(user_TB);
            Controls.Add(label1);
            Controls.Add(Sartu_BT);
            Controls.Add(logoPB);
            DoubleBuffered = true;
            Icon = (Icon)resources.GetObject("$this.Icon");
            MinimumSize = new Size(1025, 613);
            Name = "Hasiera";
            StartPosition = FormStartPosition.CenterScreen;
            Text = "HASIERA | OdooControl";
            ((System.ComponentModel.ISupportInitialize)logoPB).EndInit();
            ((System.ComponentModel.ISupportInitialize)user_PB).EndInit();
            ((System.ComponentModel.ISupportInitialize)pass_PB).EndInit();
            ((System.ComponentModel.ISupportInitialize)barra1).EndInit();
            ((System.ComponentModel.ISupportInitialize)barra2).EndInit();
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private PictureBox logoPB;
        private Button Sartu_BT;
        private Label label1;
        private TextBox user_TB;
        private TextBox pw_TB;
        private PictureBox user_PB;
        private PictureBox pass_PB;
        private PictureBox barra1;
        private PictureBox barra2;
    }
}