package by.epam.lesson14.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.lesson14.bean.entity.Product;
import by.epam.lesson14.bean.entity.ProductItem;
import by.epam.lesson14.dao.RentShop;
import by.epam.lesson14.dao.connectionpool.ConnectionPool;
import by.epam.lesson14.dao.connectionpool.exception.ConnectionPoolException;
import by.epam.lesson14.dao.exception.DaoException;

public class RentShopDAO implements RentShop {

	private final static String PRODUCT_ID = "ProductID";
	private final static String PRODUCT_CATEGORY_ID = "ProductCategoryID";
	private final static String PRODUCT_NAME = "ProductName";
	private final static String PRODUCT_BASE_PRICE = "ProductBasePrice";
	private final static String QUANTITY = "Quantity";
	private final static String AVAILABLE_QUANTITY = "AvailableQuantity";
	private final static String PRODUCT_ITEM_ID = "ProductItemID";
	private final static String MANUFACTURING_YEAR = "ManufacturingYear";
	private final static String ITEM_NOTE = "ItemNote";
	private final static String WEAR_RATE = "WearRate";
	private final static String STATUS = "Status";

	private final static String SELECT_FROM_PRODUCT = "SELECT ProductID," + "ProductCategoryID," + "ProductName,"
			+ "ProductBasePrice," + "Quantity," + "AvailableQuantity " + "FROM Product" + " WHERE ProductName LIKE ?;";

	private final static String SELECT_FROM_PRODUCT_ITEM =

			"SELECT ProductItemID, p.ProductID,ProductName,AvailableQuantity,ProductBasePrice, ManufacturingYear,ItemNote,WearRate,Status FROM ProductItem pi JOIN Product p ON pi.ProductID = p.ProductID"
					+ " WHERE AvailableQuantity >= ? AND Status = ? AND p.ProductID = ?;";

	private final static String DELETE_PRODUCT_ITEM_BY_ID = "DELETE FROM ProductItem WHERE ProductItemID = ?;";

	private final static String UPDATE_PRODUCT_BY_ID = "UPDATE Product SET "
			+ "AvailableQuantity = CASE WHEN AvailableQuantity > 0 THEN AvailableQuantity - 1 ELSE 0 END"
			+ "  FROM Product p INNER JOIN ProductItem pi ON pi.ProductID = p.ProductID WHERE ProductItemID = ?;";

	private final static String RENT_PRODUCT_ITEM = "{? = call dbo.RentOrderAdd (?,?,?)}";

	private final static String ADD_PRODUCT_ITEM = "{call dbo.AddProductItem (?,?,?,?,?,?,?,?)}";

	@Override
	public void addProductItem(int productID, int manufacturingYear, String itemNote, int status, int wearRate,
			Integer productCategoryID, String productName, Integer productBasePrice) throws DaoException {

		Connection con = null;
		CallableStatement cs = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();

		try {
			con = connectionPool.takeConnection();
		} catch (ConnectionPoolException e) {
			throw new DaoException("Connection failed.", e);
		}
		try {
			cs = con.prepareCall(ADD_PRODUCT_ITEM);
			cs.setInt(1, productID);
			cs.setInt(2, manufacturingYear);
			if (itemNote == null){
				cs.setNull(3, java.sql.Types.NVARCHAR);
				
			} else {
			cs.setString(3, itemNote);
			}
			cs.setInt(4, status);
			cs.setInt(5, wearRate);

			if (productCategoryID == null) {
				cs.setNull(6, java.sql.Types.INTEGER);
			} else {
				cs.setInt(6, productCategoryID);
			}
			if (productName == null) {
				cs.setNull(7, java.sql.Types.NVARCHAR);
			} else {
				cs.setString(7, productName);
			}
			if (productBasePrice == null) {
				cs.setNull(8, java.sql.Types.INTEGER);
			} else {
				cs.setInt(8, productBasePrice);
			}
			cs.execute();

		} catch (SQLException e1) {
			throw new DaoException("Database access error. Failed to add product item", e1);
		} finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {

				}
			}
			connectionPool.closeConnection(con);
		}

	}

	@Override
	public int rentProductItem(int productItemID, int rentPeriod, int clientID) throws DaoException {

		Connection con = null;
		CallableStatement cs = null;
		int spResult = 0;
		ConnectionPool connectionPool = ConnectionPool.getInstance();

		try {
			con = connectionPool.takeConnection();
		} catch (ConnectionPoolException e) {
			throw new DaoException("Connection failed.", e);
		}
		try {
			cs = con.prepareCall(RENT_PRODUCT_ITEM);
			cs.registerOutParameter(1, java.sql.Types.INTEGER);
			cs.setInt(2, productItemID);
			cs.setInt(3, rentPeriod);
			cs.setInt(4, clientID);

			cs.execute();
			spResult = cs.getInt(1);

		} catch (SQLException e1) {
			throw new DaoException("Database access error. Failed to rent product item", e1);
		} finally {
			if (cs != null) {
				try {
					cs.close();
				} catch (SQLException e) {

				}
			}
			connectionPool.closeConnection(con);
		}

		return spResult;
	}

	@Override
	public void deleteProductItemByID(int productItemID) throws DaoException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();

		try {
			con = connectionPool.takeConnection();
			con.setAutoCommit(false); // 1 step
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("Connection failed.", e);
		}

		try {

			ps = con.prepareStatement(UPDATE_PRODUCT_BY_ID);
			ps.setInt(1, productItemID);
			ps.executeUpdate();
			ps.close(); // 2

			ps = con.prepareStatement(DELETE_PRODUCT_ITEM_BY_ID);
			ps.setInt(1, productItemID);
			ps.executeUpdate();

			con.commit();

		} catch (SQLException e) {
			try {
				con.rollback(); // 3
			} catch (SQLException e1) {
				throw new DaoException("Failed Rollback operation.", e);
			}
			throw new DaoException("Failed data obtaining.", e);
		} finally {
			connectionPool.closeConnection(con);

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {

				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {

				}
			}

		}

	}

	@Override
	public List<Product> showProductByName(String name) throws DaoException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		List<Product> productList = new ArrayList<>();

		try {
			con = connectionPool.takeConnection();
		} catch (ConnectionPoolException e) {
			throw new DaoException("Connection failed.", e);
		}

		try {
			ps = con.prepareStatement(SELECT_FROM_PRODUCT);
			ps.setString(1, "%" + name + "%");

			rs = ps.executeQuery();

			while (rs.next()) {
				Product product = new Product();

				product.setProductID(rs.getInt(PRODUCT_ID));
				product.setProductCategoryID(rs.getInt(PRODUCT_CATEGORY_ID));
				product.setName(rs.getString(PRODUCT_NAME));
				product.setProductBasePrice(rs.getBigDecimal(PRODUCT_BASE_PRICE));
				product.setQuantity(rs.getInt(QUANTITY));
				product.setAvailableQuantity(rs.getInt(AVAILABLE_QUANTITY));
				productList.add(product);

			}

		} catch (SQLException e) {
			throw new DaoException("Failed data obtaining.", e);
		} finally {
			connectionPool.closeConnection(con);

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {

				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {

				}
			}

		}

		return productList;

	}

	@Override
	public List<ProductItem> showProductItemAvailability(int productID, int status, int availableQuantity)
			throws DaoException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		List<ProductItem> productItemList = new ArrayList<>();

		try {
			con = connectionPool.takeConnection();
		} catch (ConnectionPoolException e) {
			throw new DaoException("Connection failed.", e);
		}

		try {
			ps = con.prepareStatement(SELECT_FROM_PRODUCT_ITEM);
			ps.setInt(1, availableQuantity);
			ps.setInt(2, status);
			ps.setInt(3, productID);

			rs = ps.executeQuery();

			while (rs.next()) {
				ProductItem productItem = new ProductItem();

				productItem.setProductItemID(rs.getInt(PRODUCT_ITEM_ID));
				productItem.setProductID(rs.getInt(PRODUCT_ID));
				productItem.setName(rs.getString(PRODUCT_NAME));
				productItem.setAvailableQuantity(rs.getInt(AVAILABLE_QUANTITY));
				productItem.setProductBasePrice(rs.getBigDecimal(PRODUCT_BASE_PRICE));
				productItem.setManufacturingYear(rs.getInt(MANUFACTURING_YEAR));
				productItem.setItemNote(rs.getString(ITEM_NOTE));
				productItem.setWearRate(rs.getInt(WEAR_RATE));
				productItem.setProductID(rs.getInt(STATUS));

				productItemList.add(productItem);

			}

		} catch (SQLException e) {
			throw new DaoException("Failed data obtaining.", e);
		} finally {
			connectionPool.closeConnection(con);

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {

				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {

				}
			}

		}

		return productItemList;

	}
}

	