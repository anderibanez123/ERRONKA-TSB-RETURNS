// ***********************************************************************
// Assembly         : TSB_OdooControl_
// Author           : ikaltamirapaag2
// Created          : 11-08-2023
//
// Last Modified By : ikaltamirapaag2
// Last Modified On : 11-17-2023
// ***********************************************************************
// <copyright file="MySQLConnection.cs" company="">
//     Copyright ©  2023
// </copyright>
// <summary></summary>
// ***********************************************************************
using MySql.Data.MySqlClient;
using System.Data;
using System.Data.Odbc;
using System.Windows.Forms;

/// <summary>
/// Class MySQLConnection.
/// </summary>
public class MySQLConnection
{
    /// <summary>
    /// The connection
    /// </summary>
    private MySqlConnection connection;
    /// <summary>
    /// The connection string
    /// </summary>
    private string connectionString;

    /// <summary>
    /// Initializes a new instance of the <see cref="MySQLConnection"/> class.
    /// </summary>
    public MySQLConnection()
    {
        // MySQL konexiorako behar ditugun datuak
        connectionString = "Server=10.23.28.188;Database=tsb_db;User ID=tsb;Password=tsb";
        //connectionString = "Server=localhost;Database=tsb_db;User ID=root;Password=Ander123";
        connection = new MySqlConnection(connectionString);
    }

    // Datu baseko konexioa ireki
    /// <summary>
    /// Konexioas the ireki.
    /// </summary>
    public void KonexioaIreki()
    {
        if (connection.State == ConnectionState.Closed)
        {
            connection.Open();
        }
    }

    // Datu baseko konexioa itxi
    /// <summary>
    /// Konexioas the itxi.
    /// </summary>
    public void KonexioaItxi()
    {
        if (connection.State == ConnectionState.Open)
        {
            connection.Close();
        }
    }

    /// <summary>
    /// Gets the konexioa.
    /// </summary>
    /// <returns>MySqlConnection.</returns>
    public MySqlConnection getKonexioa()
    {
        return this.connection;
    }

    
}
