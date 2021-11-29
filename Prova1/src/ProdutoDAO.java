import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProdutoDAO {

	private Connection connection;

	public ProdutoDAO(Connection connection) {
		this.connection = connection;
	}

	public void salvar(Produto produto) throws SQLException {

		String sql = "Insert into Produto (id, nome, descricao, preco) values (?,?,?,?)";

		try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pst.setInt(1, produto.getId());
			pst.setString(2, produto.getNome());
			pst.setString(3, produto.getDescricao());
			pst.setDouble(4, produto.getPreco());

			pst.execute();

			Integer lm = pst.getUpdateCount();
			System.out.println("QTD linhas modificadas = " + lm);

			try (ResultSet rst = pst.getGeneratedKeys()) {

				while (rst.next()) {
					produto.setId(rst.getInt(1));
				}
			}
		}

	}

	public void inserir() throws SQLException {

		Produto produto1 = new Produto(1, "Notebook", "Dell", 3000.0);
		Produto produto2 = new Produto(2, "Smartphone", "Iphone 8 plus", 1800.0);
		Produto produto3 = new Produto(3, "Liquidificador", "Mondial", 150.0);

		try (Connection connection = new ConnectionFactory().recuperarConexao()) {
			ProdutoDAO filmeDAO = new ProdutoDAO(connection);
			filmeDAO.salvar(produto1);
			filmeDAO.salvar(produto2);
			filmeDAO.salvar(produto3);

		}catch (Exception e) {
			System.out.println("O banco de dados ja foi atulizado! ");
		}
	}

	public void atualizar() throws SQLException {

		ConnectionFactory criaConexao = new ConnectionFactory();
		Connection connection = criaConexao.recuperarConexao();

		Statement stm = connection.createStatement();
		stm.execute(
				"atualizar produto set nome = 'Microondas', descricao = 'Brastemp', preco = '500.0' where id = 1;");

		Integer lm = stm.getUpdateCount();
		System.out.println("Quantidade de linhas modificadas = " + lm);

	}

	public void removeSegundoProduto() throws SQLException {

		ConnectionFactory criaConexao = new ConnectionFactory();
		Connection connection = criaConexao.recuperarConexao();

		PreparedStatement stm = connection.prepareStatement("delete from produto where id = ?");
		stm.setInt(1, 2);
		stm.execute();

		Integer lm = stm.getUpdateCount();
		System.out.println("Quantidade de linhas modificadas = " + lm);
	}

}
