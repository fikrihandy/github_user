package academy.bangkit.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favUser: FavUser)

    @Query("DELETE from FavUser WHERE username = :username")
    fun delete(username: String)

    @Query("SELECT * from FavUser ORDER BY username ASC")
    fun getAllFavUsers(): LiveData<List<FavUser>>

    @Query("SELECT * FROM FavUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavUser>
}