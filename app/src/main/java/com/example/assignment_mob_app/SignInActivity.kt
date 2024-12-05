import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.example.Assignment_mob_app.R

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Navigate to CreateAccountActivity
        findViewById<TextView>(R.id.createAccountLink).setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }
    }
}
