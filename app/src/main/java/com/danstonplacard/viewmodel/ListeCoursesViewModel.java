package com.danstonplacard.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.danstonplacard.database.RoomDB;
import com.danstonplacard.database.dao.ListeCoursesDao;
import com.danstonplacard.database.model.ListeCourses;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListeCoursesViewModel extends AndroidViewModel {

    private ListeCoursesDao listeCoursesDao;
    private ExecutorService executorService;

    public ListeCoursesViewModel(@NonNull Application application) {
        super(application);
        this.listeCoursesDao = RoomDB.getDatabase(application.getApplicationContext()).listeCoursesDao();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<ListeCourses>> getListesCoursesDisponibles()
    {
        return listeCoursesDao.getAllListesCoursesDisponibles();
    }

    public LiveData<List<ListeCourses>> getListesCoursesArchivees()
    {
        return listeCoursesDao.getAllListesCoursesArchivees();
    }

    public LiveData<ListeCourses> getListeCoursesByIdLD(int idLDC)
    {
        return listeCoursesDao.getListeCoursesByIdLD(idLDC);
    }

//    public LiveData<List<Produit>> getProduitsAPrendreByIdLDC(int idLDC)
//    {
//        return listeCoursesDao.getProduitsAPrendreByIdLDC(idLDC);
//    }
}
