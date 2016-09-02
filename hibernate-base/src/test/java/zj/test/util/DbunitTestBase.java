package zj.test.util;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.xml.sax.InputSource;

import java.io.*;
import java.sql.SQLException;

/**
 * dbunit测试类基类
 *
 * @author lzj 2014/8/3
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test_base.xml")
public abstract class DbunitTestBase {

    public static IDatabaseConnection dbunitConn;
    // 临时文件，用来存储数据库的备份数据
    private File tempFile;
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * 初始化IDatabaseConnection .
     */
    @BeforeClass
    public static void setUpClass() {
        // 初始化IDatabaseConnection
        try {
            dbunitConn = new DatabaseConnection(MyDbUtil.getConnection());
        } catch (DatabaseUnitException | SQLException e) {
            e.printStackTrace();
        }
        DatabaseConfig config = dbunitConn.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
    }

    @Before
    public void setUp() {
        this.backupAllTable();
        doBefore();
    }

    // 执行必要的操作
    public abstract void doBefore();

    /**
     * 读取Xml中的table数据 .
     */
    protected void executeXml(String xml) {
        Session s = sessionFactory.openSession();
        TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
        IDataSet ds;
        try {
            if (xml == null || xml.isEmpty()) {
                ds = createDateSet("test_data");
            } else {
                ds = createDateSet(xml);
            }
            DatabaseOperation.CLEAN_INSERT.execute(dbunitConn, ds);
        } catch (IOException | DatabaseUnitException | SQLException e) {
            e.printStackTrace();
        }
    }

    protected void executeXml() {
        executeXml(null);
    }

    // 根据文件名称创建DataSet对象
    protected IDataSet createDateSet(String tname) throws DataSetException, IOException {
        InputStream is = DbunitTestBase.class.getClassLoader().getResourceAsStream(tname + ".xml");
        // 通过dtd和传入的文件创建测试的IDataSet
        FlatXmlProducer fxp = new FlatXmlProducer(new InputSource(is));
        fxp.setColumnSensing(true);
        return new FlatXmlDataSet(fxp);
    }

    // 备份数据库的所有表
    protected void backupAllTable() {
        try {
            IDataSet ds = dbunitConn.createDataSet();
            writeBackupFile(ds);
        } catch (DataSetException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    // 将表写到临时文件中
    private void writeBackupFile(IDataSet ds) throws IOException, DataSetException {
        tempFile = File.createTempFile("back", "xml");
        // 写数据表中的文件
        FlatXmlDataSet.write(ds, new FileWriter(tempFile));
    }

    // 备份指定表
    protected void backupCustomTable(String[] tname) throws DataSetException, IOException {
        QueryDataSet ds = new QueryDataSet(dbunitConn);
        for (String str : tname) {
            ds.addTable(str);
        }
        writeBackupFile(ds);
    }

    // 备份一张表
    protected void bakcupOneTable(String tname) throws DataSetException, IOException {
        backupCustomTable(new String[]{tname});
    }

    // 还原备份的表
    protected void resumeTable() throws FileNotFoundException, DatabaseUnitException, SQLException {
        IDataSet ds = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream(tempFile))));
        DatabaseOperation.CLEAN_INSERT.execute(dbunitConn, ds);
    }

    @After
    public void tearDown() {
        SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
        Session s = holder.getSession();
        s.flush();
        TransactionSynchronizationManager.unbindResource(sessionFactory);
        try {
            this.resumeTable();
        } catch (FileNotFoundException | DatabaseUnitException | SQLException e) {
            e.printStackTrace();
        }
    }

    // 清空数据
    @AfterClass
    public static void destory() {
        try {
            if (dbunitConn != null)
                dbunitConn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
