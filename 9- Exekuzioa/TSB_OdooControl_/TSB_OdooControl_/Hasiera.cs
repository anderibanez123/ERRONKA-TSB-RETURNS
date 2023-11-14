using MySql.Data.MySqlClient;
using Npgsql;
using Org.BouncyCastle.Tls;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using TSB_OdooControl;

namespace TSB_OdooControl_
{
    public partial class Hasiera : Form
    {
        // Datu baseko konstruktoreari hots egin
        private PostgreSQLConnection konexioaPostgreSQL = new PostgreSQLConnection();
        private MySQLConnection konexioaMySQL = new MySQLConnection();

        // Aplikazioaren hasiera funtzioa
        public Hasiera()
        {
            InitializeComponent();
        }

        private void sartu_BT_Click(object sender, EventArgs e)
        {
            // Karga bideua hasi
            carga.Visible = true;

            try
            {
                // MySQL konexioa ireki
                konexioaMySQL.KonexioaIreki();

                // PostgreSQL Konexioa ireki
                //konexioaPostgreSQL.konexioaIreki();

                // Erabiltzailea konprobatu
                bool ondo = erabiltzaileaKonprobatu();

                // Behin konprobatuta konexioa dugula, konexioa itxi
                konexioaMySQL.KonexioaItxi();

                // Behin konprobatuta konexioa dugula, konexioa itxi
                //konexioaPostgreSQL.konexioaItxi();

                if (ondo == true)
                {
                    // Karga bideua bukatu
                    carga.Visible = false;

                    // Hurrengo leihoa ireki
                    using (Programa programaLeihoa = new Programa())
                    {
                        // Hasiera leihoa eskutatu
                        this.Hide();

                        // Programa leihoa irakutsi
                        programaLeihoa.ShowDialog();

                        // Hasiera leihoa itxi, programa leihoa
                        this.Close();


                    }
                }

            }
            catch (Exception)
            {
                // Karga bideua bukatu
                carga.Visible = false;

                // Konexioarekin arazoa izaten ba dugu
                MessageBox.Show("Konexioarekin arazoa izan dugu.");
                throw;
            }
        }

        // Erabiltzailea konprobatzeko funtzioa
        private Boolean erabiltzaileaKonprobatu()
        {
            // Erabiltzailea eta pasahitza lortu
            String erabiltzailea = user_TB.Text.ToUpper().ToString();
            String pasahitza = pw_TB.Text.ToUpper().ToString();

            // String erabiltzaile taula lortzeko
            if (erabiltzailea.Equals("TSB") && pasahitza.Equals("TSB"))
            {
                // Kargatu beharrezko datuak kargatu
                DatuBaseDatuakKARGATU();

                // Ondo irten dela bueltatu
                return true;
            }
            else
            {
                // Karga bideua bukatu
                carga.Visible = false;

                MessageBox.Show("Sartutako erabiltzaile/pasahitza ez da zuzena.", "ERABILTZAILEA EDO PASAHITZ EZ ZUZENAK");

                using (Programa programaLeihoa = new Programa())
                {
                    // Hasiera leihoa eskutatu
                    this.Hide();

                    // Programa leihoa irakutsi
                    programaLeihoa.ShowDialog();

                    // Hasiera leihoa itxi, programa leihoa
                    this.Close();


                }
            }

            return false;
        }

        // Datu baseko datuak aktualizatu
        private void DatuBaseDatuakKARGATU()
        {

            // PROVEEDORES taulako datuak aktualizatu
            String proveedores_taula = $"SELECT r.name, c.code, r.type, r.email, r.phone, r.comment\r\nFROM public.res_partner AS r\r\nLEFT JOIN public.res_country AS c ON r.country_id = c.id\r\nWHERE r.commercial_company_name IS NOT NULL AND r.commercial_company_name != 'TSB Enpresa';";

            hornitzaileak_kargatu(proveedores_taula);

            // COMPRAS taulako datuak aktualizatu
            String compras_taula = "SELECT po.name, po.state, po.invoice_status, partner.name, company.name, po.amount_untaxed, po.amount_tax, po.amount_total, po.date_order, po.date_approve\r\nFROM public.purchase_order po\r\nLEFT JOIN public.res_partner partner ON po.partner_id = partner.id\r\nLEFT JOIN public.res_company company ON po.company_id = company.id;";

            erosketa_kargatu(compras_taula);

            // GASTOS taulako datuak aktualizatu
            String gastos_taula = "SELECT ex.name, com.name, em.name, pro.default_code, ex.payment_mode, ex.state, ex.date, ex.accounting_date, ex.description, ex.total_amount\r\nFROM public.hr_expense ex\r\nLEFT JOIN public.res_company com ON ex.company_id = com.id\r\nLEFT JOIN public.product_product pro ON ex.product_id = pro.id\r\nLEFT JOIN public.hr_employee em ON ex.employee_id = em.id";

            gastuak_kargatu(gastos_taula);

            // PRODUCTOS taulako  datuak aktualizatu
            String productos_taula = "SELECT default_code AS izena, pc.name AS Kategoria, detailed_type AS mota, list_price AS prezioa, weight AS pisua,\r\n\t\tsale_ok AS saldu_ok, purchase_ok AS erosi_ok, invoice_policy AS faktura_politika, description AS deskribapena\r\nFROM public.product_template pt\r\nLEFT JOIN public.product_category pc ON pt.categ_id = pc.id\r\nWHERE active = true;";

            produktuak_kargatu(productos_taula);

            // USUARIOS taulako datuak aktualizatu
            String usuarios_taula = "SELECT DISTINCT nombre.name, usuario.login, com.name\r\nFROM public.res_company AS com\r\nLEFT JOIN public.res_users AS usuario ON com.id = usuario.company_id\r\nLEFT JOIN public.res_partner AS nombre ON usuario.partner_id = nombre.id\r\nWHERE nombre.name NOT IN ('OdooBot', 'Default User Template', 'Portal User Template', 'Public user');";

            usuarios_kargatu(usuarios_taula);

        }

        // Hornitzaileak taula aktualizatzeko funtzioa
        private void hornitzaileak_kargatu(String selectSQL)
        {
            // Datu berriak sartu aurretik, datu zaharrak ezabatuko ditugu.
            string deleteSQL = "DELETE FROM proveedores";
            using (MySqlCommand deleteCmd = new MySqlCommand(deleteSQL, konexioaMySQL.getKonexioa()))
            {
                deleteCmd.ExecuteNonQuery();
            }

            using (NpgsqlCommand pgsqlCmd = new NpgsqlCommand(selectSQL, konexioaPostgreSQL.getKonexioa()))
            using (MySqlCommand mysqlCmd = new MySqlCommand($"INSERT INTO proveedores (izena, herria, mota, korreoa, mugikorra, komentarioa) " +
                $"VALUES (@izena, @herria, @mota, @korreoa, @mugikorra, @komentarioa)", konexioaMySQL.getKonexioa()))
            {
                using (NpgsqlDataReader reader = pgsqlCmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        // PostgreSQL-ko kontsultatik datuak lortu
                        string izenaSTR = reader.GetString(0);
                        string herriaSTR = reader.GetString(1);
                        string motaSTR = reader.GetString(2);
                        string korreoaSTR = reader.GetString(3);
                        string mugikorraSTR = reader.GetString(4);
                        string komentarioaSTR = reader.GetString(5);

                        // Sartu behar ditugun datuak zehaztu
                        mysqlCmd.Parameters.AddWithValue("@izena", izenaSTR);
                        mysqlCmd.Parameters.AddWithValue("@herria", herriaSTR);
                        mysqlCmd.Parameters.AddWithValue("@mota", motaSTR);
                        mysqlCmd.Parameters.AddWithValue("@korreoa", korreoaSTR);
                        mysqlCmd.Parameters.AddWithValue("@mugikorra", mugikorraSTR);
                        mysqlCmd.Parameters.AddWithValue("@komentarioa", komentarioaSTR);

                        try
                        {
                            // Kontsulta exekutatu
                            mysqlCmd.ExecuteNonQuery();
                        }
                        catch
                        {
                            // Karga bideua bukatu
                            carga.Visible = false;

                            MessageBox.Show("Hornitzaileak taula ezin izan dugu aktualizatu", "Hornitzaile datuak");
                            
                        }

                        // Parametroak garbitzen
                        mysqlCmd.Parameters.Clear();

                    }

                }

            }

        // Funtzio honen bukaerako parentesia
        }

        // Erosketa taula aktualizatzeko funtzioa
        private void erosketa_kargatu(String selectSQL)
        {
            // Datu berriak sartu aurretik, datu zaharrak ezabatuko ditugu.
            string deleteSQL = "DELETE FROM compras";
            using (MySqlCommand deleteCmd = new MySqlCommand(deleteSQL, konexioaMySQL.getKonexioa()))
            {
                deleteCmd.ExecuteNonQuery();
            }

            using (NpgsqlCommand pgsqlCmd = new NpgsqlCommand(selectSQL, konexioaPostgreSQL.getKonexioa()))
            using (MySqlCommand mysqlCmd = new MySqlCommand($"INSERT INTO compras (izena, estatua, faktura, klientea, enpresa, prezio_base, bez, prezio_totala, eskaera_data, baimentze_data) " +
                $"VALUES (@izena, @estatua, @faktura, @klientea, @enpresa, @prezio_base, @bez, @prezio_totala, @eskaera_data, @baimentze_data)", konexioaMySQL.getKonexioa()))
            {
                using (NpgsqlDataReader reader = pgsqlCmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        // PostgreSQL-ko kontsultatik datuak lortu
                        string izenaSTR = reader.GetString(0);
                        string estatuaSTR = reader.GetString(1);
                        string fakturaSTR = reader.GetString(2);
                        string klienteaSTR = reader.GetString(3);
                        string enpresaSTR = reader.GetString(4);
                        decimal prezio_baseDEC = reader.GetDecimal(5);
                        string prezio_baseSTR = prezio_baseDEC.ToString();
                        decimal bezDEC = reader.GetDecimal(6);
                        string bezSTR = bezDEC.ToString();
                        decimal prezio_totalaDEC = reader.GetDecimal(7);
                        string prezio_totalaSTR = prezio_totalaDEC.ToString();
                        DateTime eskaera_data = reader.GetDateTime(8);
                        DateTime baimentze_data = reader.GetDateTime(9);
                        string eskaera_dataSTR = eskaera_data.ToString("yyyy-MM-dd HH:mm:ss"); // Formato personalizado
                        string baimentze_dataSTR = baimentze_data.ToString("yyyy-MM-dd HH:mm:ss"); // Formato personalizado


                        // Sartu behar ditugun datuak zehaztu
                        mysqlCmd.Parameters.AddWithValue("@izena", izenaSTR);
                        mysqlCmd.Parameters.AddWithValue("@estatua", estatuaSTR);
                        mysqlCmd.Parameters.AddWithValue("@faktura", fakturaSTR);
                        mysqlCmd.Parameters.AddWithValue("@klientea", klienteaSTR);
                        mysqlCmd.Parameters.AddWithValue("@enpresa", enpresaSTR);
                        mysqlCmd.Parameters.AddWithValue("@prezio_base", prezio_baseSTR);
                        mysqlCmd.Parameters.AddWithValue("@bez", bezSTR);
                        mysqlCmd.Parameters.AddWithValue("@prezio_totala", prezio_totalaSTR);
                        mysqlCmd.Parameters.AddWithValue("@eskaera_data", eskaera_dataSTR);
                        mysqlCmd.Parameters.AddWithValue("@baimentze_data", baimentze_dataSTR);

                        try
                        {
                            // Kontsulta exekutatu
                            mysqlCmd.ExecuteNonQuery();
                        }
                        catch
                        {
                            // Karga bideua bukatu
                            carga.Visible = false;

                            MessageBox.Show("Erosketa taula ezin izan dugu aktualizatu", "Erosketa datuak");

                        }

                        // Parametroak garbitzen
                        mysqlCmd.Parameters.Clear();

                    }

                }

            }

            // Funtzio honen bukaerako parentesia
        }

        // Gastua taula aktualizatzeko funtzioa
        private void gastuak_kargatu(String selectSQL)
        {
            // Datu berriak sartu aurretik, datu zaharrak ezabatuko ditugu.
            string deleteSQL = "DELETE FROM gastos";
            using (MySqlCommand deleteCmd = new MySqlCommand(deleteSQL, konexioaMySQL.getKonexioa()))
            {
                deleteCmd.ExecuteNonQuery();
            }

            using (NpgsqlCommand pgsqlCmd = new NpgsqlCommand(selectSQL, konexioaPostgreSQL.getKonexioa()))
            using (MySqlCommand mysqlCmd = new MySqlCommand($"INSERT INTO gastos (izena, enpresa, eroslea, produktua, ordaindu_modua, estatua, sortze_data, ordaindu_data, deskribapena, ordaindu_prezioa) " +
                $"VALUES (@izena, @enpresa, @eroslea, @produktua, @ordaindu_modua, @estatua, @sortze_data, @ordaindu_data, @deskribapena, @ordaindu_prezioa)", konexioaMySQL.getKonexioa()))
            {
                using (NpgsqlDataReader reader = pgsqlCmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        // PostgreSQL-ko kontsultatik datuak lortu
                        string izenaSTR = konprobatuDatua(reader, 0);
                        string enpresaSTR = konprobatuDatua(reader, 1);
                        string erosleaSTR = konprobatuDatua(reader, 2);
                        string produktuaSTR = konprobatuDatua(reader, 3);
                        string ordaindu_moduaSTR = konprobatuDatua(reader, 4);
                        string estatuaSTR = konprobatuDatua(reader, 5);
                        string sortze_dataSTR = konprobatuDatua(reader, 6);
                        string ordaindu_dataSTR = konprobatuDatua(reader, 7);
                        string deskribapena = konprobatuDatua(reader, 8);
                        string ordaindu_prezioaSTR = konprobatuDatua(reader, 9);


                        // Sartu behar ditugun datuak zehaztu
                        mysqlCmd.Parameters.AddWithValue("@izena", izenaSTR);
                        mysqlCmd.Parameters.AddWithValue("@enpresa", enpresaSTR);
                        mysqlCmd.Parameters.AddWithValue("@eroslea", erosleaSTR);
                        mysqlCmd.Parameters.AddWithValue("@produktua", produktuaSTR);
                        mysqlCmd.Parameters.AddWithValue("@ordaindu_modua", ordaindu_moduaSTR);
                        mysqlCmd.Parameters.AddWithValue("@estatua", estatuaSTR);
                        mysqlCmd.Parameters.AddWithValue("@sortze_data", sortze_dataSTR);
                        mysqlCmd.Parameters.AddWithValue("@ordaindu_data", ordaindu_dataSTR);
                        mysqlCmd.Parameters.AddWithValue("@deskribapena", deskribapena);
                        mysqlCmd.Parameters.AddWithValue("@ordaindu_prezioa", ordaindu_prezioaSTR);

                        try
                        {
                            // Kontsulta exekutatu
                            mysqlCmd.ExecuteNonQuery();
                        }
                        catch
                        {
                            // Karga bideua bukatu
                            carga.Visible = false;

                            MessageBox.Show("Gastuak taula ezin izan dugu aktualizatu", "Gastuak datuak");

                        }

                        // Parametroak garbitzen
                        mysqlCmd.Parameters.Clear();

                    }

                }

            }

            // Funtzio honen bukaerako parentesia
        }

        // Produktuak taula aktualizatzzeko funtzioa
        private void produktuak_kargatu(String selectSQL)
        {

            // Datu berriak sartu aurretik, datu zaharrak ezabatuko ditugu.
            string deleteSQL = "DELETE FROM productos";
            using (MySqlCommand deleteCmd = new MySqlCommand(deleteSQL, konexioaMySQL.getKonexioa()))
            {
                deleteCmd.ExecuteNonQuery();
            }

            using (NpgsqlCommand pgsqlCmd = new NpgsqlCommand(selectSQL, konexioaPostgreSQL.getKonexioa()))
            using (MySqlCommand mysqlCmd = new MySqlCommand($"INSERT INTO productos (izena, kategoria, mota, prezioa, pisua, saldu_ok, erosi_ok, faktura_politika, deskribapena) " +
                $"VALUES (@izena, @kategoria, @mota, @prezioa, @pisua, @saldu_ok, @erosi_ok, @faktura_politika, @deskribapena)", konexioaMySQL.getKonexioa()))
            {
                using (NpgsqlDataReader reader = pgsqlCmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        // PostgreSQL-ko kontsultatik datuak lortu
                        string izenaSTR = konprobatuDatua(reader, 0);
                        string kategoriaSTR = konprobatuDatua(reader, 1);
                        string motaSTR = konprobatuDatua(reader, 2);
                        string prezioaSTR = konprobatuDatua(reader, 3);
                        string pisuaSTR = konprobatuDatua(reader, 4);
                        string saldu_okSTR = konprobatuDatua(reader, 5);
                        string erosi_okSTR = konprobatuDatua(reader, 6);
                        string faktura_politikaSTR = konprobatuDatua(reader, 7);
                        string deskribapenaSTR = konprobatuDatua(reader, 8);

                        // Sartu behar ditugun datuak zehaztu
                        mysqlCmd.Parameters.AddWithValue("@izena", izenaSTR);
                        mysqlCmd.Parameters.AddWithValue("@kategoria", kategoriaSTR);
                        mysqlCmd.Parameters.AddWithValue("@mota", motaSTR);
                        mysqlCmd.Parameters.AddWithValue("@prezioa", prezioaSTR);
                        mysqlCmd.Parameters.AddWithValue("@pisua", pisuaSTR);
                        mysqlCmd.Parameters.AddWithValue("@saldu_ok", saldu_okSTR);
                        mysqlCmd.Parameters.AddWithValue("@erosi_ok", erosi_okSTR);
                        mysqlCmd.Parameters.AddWithValue("@faktura_politika", faktura_politikaSTR);
                        mysqlCmd.Parameters.AddWithValue("@deskribapena", deskribapenaSTR);

                        try
                        {
                            // Kontsulta exekutatu
                            mysqlCmd.ExecuteNonQuery();
                        }
                        catch
                        {
                            // Karga bideua bukatu
                            carga.Visible = false;

                            MessageBox.Show("Produktuak taula ezin izan dugu aktualizatu", "Produktuak datuak");

                        }

                        // Parametroak garbitzen
                        mysqlCmd.Parameters.Clear();

                    }

                }

            }

        }

        // Erabiltzaileak taula aktualizatzeko funtzioa
        private void usuarios_kargatu(String selectSQL)
        {

            // Datu berriak sartu aurretik, datu zaharrak ezabatuko ditugu.
            string deleteSQL = "DELETE FROM usuarios";
            using (MySqlCommand deleteCmd = new MySqlCommand(deleteSQL, konexioaMySQL.getKonexioa()))
            {
                deleteCmd.ExecuteNonQuery();
            }

            using (NpgsqlCommand pgsqlCmd = new NpgsqlCommand(selectSQL, konexioaPostgreSQL.getKonexioa()))
            using (MySqlCommand mysqlCmd = new MySqlCommand($"INSERT INTO usuarios (erabiltzailea, email, enpresa) " +
                $"VALUES (@erabiltzailea, @email, @mota)", konexioaMySQL.getKonexioa()))
            {
                using (NpgsqlDataReader reader = pgsqlCmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        // PostgreSQL-ko kontsultatik datuak lortu
                        string erabiltzaileaSTR = konprobatuDatua(reader, 0);
                        string emailSTR = konprobatuDatua(reader, 1);
                        string motaSTR = konprobatuDatua(reader, 2);
                        

                        // Sartu behar ditugun datuak zehaztu
                        mysqlCmd.Parameters.AddWithValue("@erabiltzailea", erabiltzaileaSTR);
                        mysqlCmd.Parameters.AddWithValue("@email", emailSTR);
                        mysqlCmd.Parameters.AddWithValue("@mota", motaSTR);


                        try
                        {
                            // Kontsulta exekutatu
                            mysqlCmd.ExecuteNonQuery();
                        }
                        catch
                        {
                            // Karga bideua bukatu
                            carga.Visible = false;

                            MessageBox.Show("Usuarios taula ezin izan dugu aktualizatu", "Usuarios datuak");

                        }

                        // Parametroak garbitzen
                        mysqlCmd.Parameters.Clear();

                    }

                }

            }

        }


        // Datu baseko datuak lortzerakoan, konprobatu null al diren edo ez, eta beste mota batekoak direnak String moduan bihurtzeko
        public static string konprobatuDatua(NpgsqlDataReader reader, int kolumnak)
        {
            if (!reader.IsDBNull(kolumnak))
            {
                if (reader.GetFieldType(kolumnak) == typeof(DateTime))
                {
                    // Datua DateTime bada, string moduan bihurtu formatu honekin
                    return reader.GetDateTime(kolumnak).ToString("yyyy-MM-dd HH:mm:ss");
                }
                else if (reader.GetFieldType(kolumnak) == typeof(decimal))
                {
                    // Decimal motakoa bada, string moduan bihurtu
                    return reader.GetDecimal(kolumnak).ToString();
                }
                else if (reader.GetFieldType(kolumnak) == typeof(bool))
                {
                    // Boolean mota badugu, string bezala bueltatuko dugu
                    return reader.GetBoolean(kolumnak).ToString();
                }
                else
                {
                    // Beste kasuetan, string moduan bueltatu null ez bada
                    return reader.GetString(kolumnak);
                }
            }
            // Null ba dugu datua, empty bezala bueltatu
            return string.Empty;
        }

    }
}
