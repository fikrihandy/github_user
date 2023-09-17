package academy.bangkit.githubuser.ui.detail

import academy.bangkit.githubuser.database.FavUser
import academy.bangkit.githubuser.database.repository.FavUserRepository
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class FavUserAddUpdateViewModel(application: Application) : ViewModel() {
    private val mFavUserRepository: FavUserRepository = FavUserRepository(application)

    fun insert(favUser: FavUser) {
        mFavUserRepository.insert(favUser)
    }

    fun delete(username: String) {
        mFavUserRepository.delete(username)
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavUser> {
        return mFavUserRepository.getFavoriteUserByUsername(username)
    }

    fun getAllFavUsers(): LiveData<List<FavUser>> {
        return mFavUserRepository.getAllFavUsers()
    }
}