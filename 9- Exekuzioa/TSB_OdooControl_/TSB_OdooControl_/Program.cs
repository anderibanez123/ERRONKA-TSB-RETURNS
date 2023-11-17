// ***********************************************************************
// Assembly         : TSB_OdooControl_
// Author           : ikaltamirapaag2
// Created          : 11-06-2023
//
// Last Modified By : ikaltamirapaag2
// Last Modified On : 11-17-2023
// ***********************************************************************
// <copyright file="Program.cs" company="">
//     Copyright ©  2023
// </copyright>
// <summary></summary>
// ***********************************************************************
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TSB_OdooControl_
{
    /// <summary>
    /// Class Program.
    /// </summary>
    internal static class Program
    {
        /// <summary>
        /// Punto de entrada principal para la aplicación.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Hasiera());
        }
    }
}
