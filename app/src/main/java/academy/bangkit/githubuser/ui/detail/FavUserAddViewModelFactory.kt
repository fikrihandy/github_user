package academy.bangkit.githubuser.ui.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavUserAddViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: FavUserAddViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavUserAddViewModelFactory {
            if (INSTANCE == null) {
                synchronized(FavUserAddViewModelFactory::class.java) {
                    INSTANCE = FavUserAddViewModelFactory(application)
                }
            }
            return INSTANCE as FavUserAddViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavUserAddUpdateViewModel::class.java)) {
            return FavUserAddUpdateViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}