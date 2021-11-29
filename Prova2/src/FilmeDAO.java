import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import modelo.Filme;

public class FilmeDAO {

	private Connection connection;

	public FilmeDAO(Connection connection) {
		this.connection = connection;
	}

	public void salvar(Filme filme) throws SQLException {

		String sql = "Insert into Filme (id, nome, descricao, ano) values (?,?,?,?)";

		try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pst.setInt(1, filme.getId());
			pst.setString(2, filme.getNome());
			pst.setString(3, filme.getDescricao());
			pst.setInt(4, filme.getAno());

			pst.execute();

			try (ResultSet rst = pst.getGeneratedKeys()) {

				while (rst.next()) {
					filme.setId(rst.getInt(1));
				}
			}
		}

	}

	public void AdicionarFilmes() throws SQLException {

		Filme HarryFilosofal = new Filme(1, "Harry Potter e a Pedra Filosofal", "Literatura fantástica", 2001);
		Filme HarrySecreta = new Filme(2, "Harry Potter e a Câmara Secreta", "Literatura fantástica", 2002);
		Filme HarryAzkaban = new Filme(3, "Harry Potter e o Prisioneiro de Azkaban", "Literatura fantástica", 2004);
		Filme HarryCálice  = new Filme(4, "Harry Potter e o Cálice de Fogo", "Literatura fantástica", 2005);
		Filme HarryFênix = new Filme(5, "Harry Potter e a Ordem da Fênix", "Literatura fantástica", 2007);
		Filme HarryPríncipe = new Filme(6, "Harry Potter e o Enigma do Príncipe", "Literatura fantástica", 2009);
		Filme HarryRelíquias1 = new Filme(7, "Harry Potter e as Relíquias da Morte: Parte 1", "Literatura fantástica", 2010);
		Filme HarryRelíquias2 = new Filme(8, "Harry Potter e as Relíquias da Morte: Parte 2", "Literatura fantástica", 2011);
		Filme Vorazes = new Filme(9, "Jogos Vorazes", "Aventura", 2012);
		Filme VorazesChamas = new Filme(10, "Jogos Vorazes – Em Chamas", "Aventura", 2013);
		Filme VorazesEsperança1 = new Filme(11, "Jogos Vorazes: A Esperança – Parte 1", "Aventura", 2014);
		Filme VorazesEsperançaFinal = new Filme(12, "Jogos Vorazes: A Esperança – O Final ", "Aventura", 2015);
		Filme MazeCorrer = new Filme(13, "Maze Runner: Correr ou Morrer", "Ficção Científica", 2014);
		Filme MazeProva = new Filme(14, "Maze Runner: Prova de Fogo", "Ficção Científica", 2015);
		Filme MazeCura = new Filme(15, "Maze Runner: A Cura Mortal", "Ficção Científica", 2018);
		Filme HobbitJornada = new Filme(16, "O Hobbit: Uma Jornada Inesperada", "Literatura fantástica", 2012);
		Filme HobbitSmaug = new Filme(17, "O Hobbit: A Desolação de Smaug", "Literatura fantástica", 2013);
		Filme SenhorAnel = new Filme(18, "O Senhor dos Anéis: A Sociedade do Anel", "Literatura fantástica", 2001);
		Filme SenhorTorres  = new Filme(19, "O Senhor dos Anéis: As Duas Torres ", "Literatura fantástica", 2002);
		Filme SenhorRetorno = new Filme(20, "O Senhor dos Anéis: O Retorno do Rei", "Literatura fantástica", 2003);

		try (Connection connection = new ConnectionFactory().recuperarConexao()) {
			FilmeDAO filmeDAO = new FilmeDAO(connection);
			filmeDAO.salvar(HarryFilosofal);
			filmeDAO.salvar(HarrySecreta);
			filmeDAO.salvar(HarryAzkaban);
			filmeDAO.salvar(HarryCálice);
			filmeDAO.salvar(HarryFênix);
			filmeDAO.salvar(HarryPríncipe);
			filmeDAO.salvar(HarryRelíquias1);
			filmeDAO.salvar(HarryRelíquias2);
			filmeDAO.salvar(Vorazes);
			filmeDAO.salvar(VorazesChamas);
			filmeDAO.salvar(VorazesEsperança1);
			filmeDAO.salvar(VorazesEsperançaFinal);
			filmeDAO.salvar(MazeCorrer);
			filmeDAO.salvar(MazeProva);
			filmeDAO.salvar(MazeCura);
			filmeDAO.salvar(HobbitJornada);
			filmeDAO.salvar(HobbitSmaug);
			filmeDAO.salvar(SenhorAnel);
			filmeDAO.salvar(SenhorTorres);
			filmeDAO.salvar(SenhorRetorno);

			System.out.println("O banco de dados foi atualiado com os 20 filmes!\n");

		} catch (Exception e) {
			System.out.println("O banco de dados já foi atualizado\n");
		}

	}

	public List<Filme> listar() throws SQLException {

		List<Filme> filmes = new ArrayList<Filme>();

		String sql = "select * from filme";

		try (PreparedStatement pstm = connection.prepareStatement(sql)) {

			pstm.execute();

			try (ResultSet rst = pstm.getResultSet()) {
				while (rst.next()) {
					Filme filme = new Filme(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getInt(4));

					filmes.add(filme);

				}
			}

		}

		return filmes;
	}

	public void menuPrincipal() throws SQLException {
		Scanner in = new Scanner(System.in);

		FilmeDAO filmeDAO = new FilmeDAO(connection);

		int opcao = 0, pagina = 0, qtFilmes = 0;

		do {
			System.out.print("Digite a pagina que deseja acessar, de 1 a 5 : ");
			opcao = in.nextInt();

			if (opcao == 1)
				pagina = 0;

			else if (opcao == 2)
				pagina = 4;

			else if (opcao == 3)
				pagina = 8;

			else if (opcao == 4)
				pagina = 12;

			else if (opcao == 5)
				pagina = 16;

			else
				System.out.println("Página não existe, tente novamente\n");

		} while (opcao <= 0 || opcao > 5);

		do {
			System.out.print("Quantos filmes deseja ver? : ");
			qtFilmes = in.nextInt();

			if (qtFilmes <= 0 || qtFilmes > 20)
				System.out.println("Valor inválido, digite de 1 a 20 !");

		} while (qtFilmes <= 0 || qtFilmes > 20);

		List<Filme> listaDeFilmes = filmeDAO.listar();

		if (pagina + qtFilmes > listaDeFilmes.size())
			System.out.println("Erro: Quantidade de filmes não existente na página!");
		else
			listaDeFilmes.subList(pagina, pagina + qtFilmes).forEach(System.out::println);

		in.close();

	}

}
