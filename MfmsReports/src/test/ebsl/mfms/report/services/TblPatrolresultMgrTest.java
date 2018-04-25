package ebsl.mfms.report.services;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import ebsl.mfms.report.daos.DaoBase;
import ebsl.mfms.report.models.sos.ExportPatrolInspectionRoutineSo;
import ebsl.mfms.report.models.sos.ExportPatrolRoutineSo;
import ebsl.mfms.report.models.vos.ExportPatrolInspectionRoutineVo;
import ebsl.mfms.report.models.vos.ExportPatrolRoutineVo;

public class TblPatrolresultMgrTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReadByExportPatrolInspectionRoutineSo() {
		try {
			TblPatrolresultMgr mgr = new TblPatrolresultMgr(DaoBase.CONNECTION_TYPE_JDBC);
			
			ExportPatrolInspectionRoutineSo so = new ExportPatrolInspectionRoutineSo();
			so.setSiteKey(5);
			List<ExportPatrolInspectionRoutineVo> voList = mgr.readByExportPatrolInspectionRoutineSo(so);


			if (voList != null) {
				Assert.assertTrue(voList.size() > 0);
			} else {
				Assert.assertNull(voList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	@Ignore
	@Test
	public void testReadByExportPatrolRoutineSo() {
		fail("Not yet implemented");
	}
	@Ignore
	@Test
	public void testRead() {
		fail("Not yet implemented");
	}
	@Ignore
	@Test
	public void testCreate() {
		fail("Not yet implemented");
	}
	@Ignore
	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}
	@Ignore
	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

}
