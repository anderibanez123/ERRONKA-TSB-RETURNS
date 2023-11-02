using System;
using System.Data;
using Npgsql;

namespace TSB_OdooControl
{
    internal class PostgreSQLConnection
    {
        private string connectionString;
        private NpgsqlConnection connection;

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

        public void konexioaIreki()
        {
            if (connection.State == ConnectionState.Closed)
            {
                connection.Open();
            }
        }

        public void konexioaItxi()
        {
            if (connection.State == ConnectionState.Open)
            {
                connection.Close();
            }
        }

        public NpgsqlConnection konexioaLortu()
        {
            return connection;
        }
    }
}
