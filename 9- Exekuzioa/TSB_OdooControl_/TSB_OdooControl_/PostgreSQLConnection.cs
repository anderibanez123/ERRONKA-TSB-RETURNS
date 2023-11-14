using System;
using System.Data;
using System.Windows.Forms;
using Npgsql;

namespace TSB_OdooControl
{
    internal class PostgreSQLConnection
    {
        // Parametroak
        private string connectionString;
        private NpgsqlConnection connection;

        // Konexioa zehazteko datuak
        private string host = "10.23.28.188:8068";
        private string database = "tsb";
        private string username = "tsb";
        private string password = "tsb";

        public PostgreSQLConnection()
        {
            // konexioa irekitzeko string-a
            connectionString = $"Host={this.host};Database={this.database};Username={this.username};Password={this.password}";
            connection = new NpgsqlConnection(connectionString);
        }

        // PostgreSQLko Konexioa ireki ahal izateko
        public void konexioaIreki()
        {
            if (connection.State == ConnectionState.Closed)
            {
                connection.Open();
            }
        }

        // PostgreSQLko Konexioa itxi ahal izateko
        public void konexioaItxi()
        {
            if (connection.State == ConnectionState.Open)
            {
                connection.Close();
            }
        }

        // PostgreSQLko Konexioa lortu ahal izateko
        public NpgsqlConnection getKonexioa()
        {
            return this.connection;
        }


    }
}
