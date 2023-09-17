package academy.bangkit.githubuser.database.repository

import academy.bangkit.githubuser.database.FavUser
import academy.bangkit.githubuser.database.FavUserDao
import academy.bangkit.githubuser.database.FavUserRoomDatabase
import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavUserRepository(application: Application) {
    private val mFavUserDao: FavUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavUserRoomDatabase.getDatabase(application)
        mFavUserDao = db.favUserDao()
    }

    fun getAllFavUsers(): LiveData<List<FavUser>> {
        return mFavUserDao.getAllFavUsers()
    }

    fun insert(favUser: FavUser) {
        executorService.execute { mFavUserDao.insert(favUser) }
    }

    fun delete(username: String) {
        executorService.execute { mFavUserDao.delete(username) }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavUser> {
        return mFavUserDao.getFavoriteUserByUsername(username)
    }
}