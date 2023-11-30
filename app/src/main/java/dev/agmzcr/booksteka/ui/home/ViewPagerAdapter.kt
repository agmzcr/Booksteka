package dev.agmzcr.booksteka.ui.home

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import dev.agmzcr.booksteka.ui.home.reading.ReadingFragment
import dev.agmzcr.booksteka.ui.home.wish.WishFragment

class ViewPagerAdapter(
    private val context: Context,
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount() = 2

    override fun getItem(position: Int) = when(position) {
        0 -> ReadingFragment()
        1 -> WishFragment()
        else -> throw IllegalStateException("Unexpected position $position")
    }

    override fun getPageTitle(position: Int): CharSequence = when(position) {
        0 -> "Leyendo"
        1 -> "Deseados"
        else -> throw IllegalStateException("Unexpected position $position")
    }
}