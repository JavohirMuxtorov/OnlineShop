package com.example.savdo.screen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.savdo.R
import com.example.savdo.screen.cart.CartFragment
import com.example.savdo.screen.favorite.FavoriteFragment
import com.example.savdo.screen.home.HomeFragment
import com.example.savdo.screen.profile.ProfileFragment
import com.example.savdo.utils.LocaleManager
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus

class MainActivity : AppCompatActivity() {
    val homeFragment = HomeFragment.newInstance()
    val favoriteFragment = FavoriteFragment.newInstance()
    val cartFragment = CartFragment.newInstance()
    val profileFragment = ProfileFragment.newInstance()
    var activeFragment: Fragment = homeFragment

    var languages = arrayOf("","UZBEK", "ENGLISH")

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.productData.observe(this, Observer {
            viewModel.InsertAllProducts2DB(it)
            EventBus.getDefault().post(loadData())
        })
        viewModel.categoryData.observe(this, Observer {
            viewModel.InsertAllCategory2DB(it)
            EventBus.getDefault().post(loadData())
        })
        menu.setOnClickListener {
            drawer.openDrawer(navView)
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, homeFragment, homeFragment.tag).hide(homeFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, favoriteFragment, favoriteFragment.tag).hide(favoriteFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, cartFragment, cartFragment.tag).hide(cartFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.flContainer, profileFragment, profileFragment.tag).hide(profileFragment)
            .commit()

        supportFragmentManager.beginTransaction().show(activeFragment).commit()

        BottomNavigationView.setOnItemSelectedListener {
            if (it.itemId == R.id.actionHome) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(homeFragment)
                    .commit()
                activeFragment = homeFragment
            } else if (it.itemId == R.id.actionFavorite) {
                supportFragmentManager.beginTransaction().hide(activeFragment)
                    .show(favoriteFragment).commit()
                activeFragment = favoriteFragment
            } else if (it.itemId == R.id.actionCart) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(cartFragment)
                    .commit()
                activeFragment = cartFragment
            } else if (it.itemId == R.id.actionProfil) {
                supportFragmentManager.beginTransaction().hide(activeFragment).show(profileFragment)
                    .commit()
                activeFragment = profileFragment
            }


            return@setOnItemSelectedListener true
        }



        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, languages)
            spinner.adapter = adapter
         spinner.onItemSelectedListener = object :
           AdapterView.OnItemSelectedListener {
             override fun onItemSelected(parent: AdapterView<*>,
                                         view: View, position: Int, id: Long) {
                 if (languages[position]=="UZBEK"){
                     select.text = "Uzbek"
                 }else if (languages[position]=="ENGLISH"){
                     select.text = "English"
                 }
                save.setOnClickListener {
                    if (languages[position]=="UZBEK") {
                        Hawk.put("pref_lang", "uz")
                        Toast.makeText(this@MainActivity, "uzbek", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@MainActivity, MainActivity::class.java))
                        finish()
//                        drawer.closeDrawer(navView)
                    } else if (languages[position]=="ENGLISH") {
                        Hawk.put("pref_lang", "en")
                        Toast.makeText(this@MainActivity, "english", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@MainActivity,MainActivity::class.java))
                        finish()
//                        drawer.closeDrawer(navView)
                    }  else {
                        Toast.makeText(this@MainActivity, "Til o'zgartirilmadi", Toast.LENGTH_LONG).show()
                    }
                }

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        loadData()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }
    fun loadData(){
        viewModel.getTopProducts()
        viewModel.getCategories()
    }
    }




//save.setOnClickListener {
//                    if (languages[position] == "UZBEK") {
//                        Hawk.put("pref_lang", "uz")
//                        startActivity(Intent(applicationContext,MainActivity::class.java))
//                        finish()
//                        drawer.closeDrawer(navView)
//                    } else if (languages[position] == "ENGLISH") {
//                        Hawk.put("pref_lang", "en")
//                        Toast.makeText(applicationContext, "english", Toast.LENGTH_LONG).show()
//                        startActivity(Intent(applicationContext, MainActivity::class.java))
//                        finish()
//                        drawer.closeDrawer(navView)
//                    } else {
//                        Toast.makeText(applicationContext, "Til o'zgartirilmadi", Toast.LENGTH_LONG).show()
//                    }
//                }
//
