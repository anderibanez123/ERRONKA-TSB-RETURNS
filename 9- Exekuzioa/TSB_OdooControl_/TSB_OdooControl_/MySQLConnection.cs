using MySql.Data.MySqlClient;
using System.Data;
using System.Windows.Forms;

public class MySQLConnection
{
    private MySqlConnection connection;
    private string connectionString;

    public MySQLConnection()
    {
        // MySQL konexiorako behar ditugun datuak
        connectionString = "Server=10.23.28.188;Database=tsb_db;User ID=tsb;Password=tsb";
        //connectionString = "Server=localhost;Database=tsb_db;User ID=root;Password=Ander123";
        connection = new MySqlConnection(connectionString);
    }

    // Datu baseko konexioa ireki
    public void KonexioaIreki()
    {
        if (connection.State == ConnectionState.Closed)
        {
            connection.Open();
        }
    }

    // Datu baseko konexioa itxi
    public void KonexioaItxi()
    {
        if (connection.State == ConnectionState.Open)
        {
            connection.Close();
        }
    }

    public MySqlConnection getKonexioa()
    {
        return this.connection;
    }

    
}
