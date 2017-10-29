//package cz.fi.muni.pa165.teamred.dao;
//
//import cz.fi.muni.pa165.teamred.PersistenceSampleApplicationContext;
//import cz.fi.muni.pa165.teamred.entity.Sample;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
//import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
//import org.springframework.transaction.annotation.Transactional;
//import org.testng.annotations.Test;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Transactional
//@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
//@TestExecutionListeners(TransactionalTestExecutionListener.class)
//public class SampleDaoTest extends AbstractTestNGSpringContextTests {
//    @Autowired
//    private SampleDao sampleDao;
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Test
//    void test() {
//        Sample sample1 = new Sample();
//        Sample sample2 = new Sample();
//
//        sample1.setName("first");
//        sample2.setName("second");
//
//        sampleDao.create(sample1);
//        sampleDao.create(sample2);
//
//        em.flush();
//
//        assertThat(sampleDao.findById(sample1.getId()).getName()).isEqualTo("first");
//        assertThat(sampleDao.findById(sample2.getId()).getName()).isEqualTo("second");
//    }
//}
