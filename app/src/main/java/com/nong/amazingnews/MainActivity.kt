package com.nong.amazingnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nong.amazingnews.databinding.ActivityMainBinding
import com.nong.amazingnews.fragment.EveryNewsFragment
import com.nong.amazingnews.fragment.SearchNewsFragment
import com.nong.amazingnews.fragment.SettingsFragment

enum class FragmentInfo(val tag: String) {
    EVERY_NEWS_FRAGMENT("everyNews"),
    SEARCH_NEWS_FRAGMENT("searchNews"),
    SETTINGS_FRAGMENT("settingsNews")
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeFragment(FragmentInfo.EVERY_NEWS_FRAGMENT)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.every_news_list -> {
                    changeFragment(FragmentInfo.EVERY_NEWS_FRAGMENT)
                }
                R.id.search_news -> {
                    changeFragment(FragmentInfo.SEARCH_NEWS_FRAGMENT)
                }
                R.id.settings -> {
                    changeFragment(FragmentInfo.SETTINGS_FRAGMENT)
                }
            }
            true
        }
    }

    private fun changeFragment(fragmentInfo: FragmentInfo) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment = supportFragmentManager.findFragmentByTag(fragmentInfo.tag)

        if (fragment == null) {
            fragment = when (fragmentInfo) {
                FragmentInfo.EVERY_NEWS_FRAGMENT -> EveryNewsFragment()
                FragmentInfo.SEARCH_NEWS_FRAGMENT -> SearchNewsFragment()
                FragmentInfo.SETTINGS_FRAGMENT -> SettingsFragment()
            }
            fragmentTransaction.add(R.id.main_container, fragment, fragmentInfo.tag)
        }

        fragmentTransaction.show(fragment)

        FragmentInfo.values()
            .filterNot { it == fragmentInfo }
            .forEach {
                when (val pastFragment = supportFragmentManager.findFragmentByTag(it.tag)) {
                    null -> return@forEach
                    else -> fragmentTransaction.hide(pastFragment)
                }
            }

        fragmentTransaction.commit()
    }
}