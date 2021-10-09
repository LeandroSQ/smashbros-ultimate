package quevedo.soares.leandro.techtest.extension

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

fun ViewPager2.setupWithIndicator(tabLayout: TabLayout) {
	TabLayoutMediator(tabLayout, this) { _, _ -> }.attach()
}

typealias ViewPagerPageChangeCallback = (position: Int) -> Unit
fun ViewPager2.doOnPageChange(callback: ViewPagerPageChangeCallback) {
	this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
		override fun onPageSelected(position: Int) {
			callback(position)
		}
	})
}