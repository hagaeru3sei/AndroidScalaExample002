package org.nmochizuki.scalaexample002

import android.content.Context
import android.support.v4.app.{Fragment, FragmentManager, FragmentPagerAdapter}
import org.nmochizuki.scalaexample002.fragment.{App1Fragment, App2Fragment}

class AppPagerAdapter(fm: FragmentManager, context: Context)
    extends FragmentPagerAdapter(fm) {

  override def getItem(position: Int): Fragment = {
    Tabs(position) match {
      case Some(tab) =>
        // TODO: refactor
        if (tab.pageNumber == 0) new App1Fragment
        else if (tab.pageNumber == 1) new App2Fragment
        else null
      case _ => null
    }
  }

  override def getCount: Int = Tabs.values.size

  override def getPageTitle(position: Int): CharSequence = {
    super.getPageTitle(position)
    s"TAB : $position"
  }

}

sealed abstract class Tabs(val pageNumber: Int)

object Tabs {

  case object Tab1 extends Tabs(0)
  case object Tab2 extends Tabs(1)

  val values: Map[Int, Tabs with Product with Serializable] =
      Seq(Tab1, Tab2).map(t => t.pageNumber -> t).toMap

  def apply(pageNumber: Int): Option[Tabs] = values.get(pageNumber)

}
