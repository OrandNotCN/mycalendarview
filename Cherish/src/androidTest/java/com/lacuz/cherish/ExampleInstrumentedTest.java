package com.lacuz.cherish;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.lacuz.cherish.greendao.entity.Birth;
import com.lacuz.cherish.greendao.gen.BirthDao;
import com.lacuz.cherish.greendao.gen.DaoMaster;
import com.lacuz.cherish.greendao.gen.DaoSession;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    DaoMaster daoMaster;
    DaoSession daoSession;
    BirthDao birthDao;
    Context appContext;
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.myproject", appContext.getPackageName());
        getStuDao();
//        addTest();
        queryList();
    }

    private void queryList() {
        List<Birth> stuList = birthDao.queryBuilder().list();
        if (stuList != null) {
            String searchAllInfo = "";
            for (int i = 0; i < stuList.size(); i++) {
                Birth stu = stuList.get(i);
                searchAllInfo += stu.toString()+ "\n";
            }
            System.out.println(searchAllInfo);
        }

    }

    private void getStuDao() {
        // 创建数据
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(appContext, "cherish.db", null);
        daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
        birthDao = daoSession.getBirthDao();
    }

    private void addTest(){
        birthDao.insert(Birth.createEntity());
        birthDao.insert(Birth.createEntity());
        birthDao.insert(Birth.createEntity());
        birthDao.insert(Birth.createEntity());
        birthDao.insert(Birth.createEntity());
        birthDao.insert(Birth.createEntity());
    }

}
