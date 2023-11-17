// ***********************************************************************
// Assembly         : TSB_OdooControl_
// Author           : ikaltamirapaag2
// Created          : 11-06-2023
//
// Last Modified By : ikaltamirapaag2
// Last Modified On : 11-15-2023
// ***********************************************************************
// <copyright file="PostgreSQLConnection.cs" company="">
//     Copyright ©  2023
// </copyright>
// <summary></summary>
// ***********************************************************************
using System;
using System.Data;
using System.Windows.Forms;
using Npgsql;

namespace TSB_OdooControl
{
    /// <summary>
    /// Class PostgreSQLConnection.
    /// </summary>
    internal class PostgreSQLConnection
    {
        // Parametroak
        /// <summary>
        /// The connection string
        /// </summary>
        private string connectionString;
        /// <summary>
        /// The connection
        /// </summary>
        private NpgsqlConnection connection;

        // Konexioa zehazteko datuak
        /// <summary>
        /// The host
        /// </summary>
        private string host = "10.23.28.188:8068";
        /// <summary>
        /// The database
        /// </summary>
        private string database = "tsb";
        /// <summary>
        /// The username
        /// </summary>
        private string username = "tsb";
        /// <summary>
        /// The password
        /// </summary>
        private string password = "tsb";


        /// <summary>
        /// Initializes a new instance of the <see cref="PostgreSQLConnection"/> class.
        /// </summary>
        public PostgreSQLConnection()
        {
            // konexioa irekitzeko string-a
            connectionString = $"Host={this.host};Database={this.database};Username={this.username};Password={this.password}";
            connection = new NpgsqlConnection(connectionString);
        }

        // PostgreSQLko Konexioa ireki ahal izateko
        /// <summary>
        /// Konexioas the ireki.
        /// </summary>
        public void konexioaIreki()
        {
            if (connection.State == ConnectionState.Closed)
            {
                connection.Open();
            }
        }

        // PostgreSQLko Konexioa itxi ahal izateko
        /// <summary>
        /// Konexioas the itxi.
        /// </summary>
        public void konexioaItxi()
        {
            if (connection.State == ConnectionState.Open)
            {
                connection.Close();
            }
        }

        // PostgreSQLko Konexioa lortu ahal izateko
        /// <summary>
        /// Gets the konexioa.
        /// </summary>
        /// <returns>NpgsqlConnection.</returns>
        public NpgsqlConnection getKonexioa()
        {
            return this.connection;
        }


    }
}
