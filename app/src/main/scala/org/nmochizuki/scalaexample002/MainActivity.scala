package org.nmochizuki.scalaexample002

import android.app._
import android.content.pm.PackageManager
import android.content._
import android.os.{Bundle, IBinder}
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.Manifest
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import org.nmochizuki.scalaexample002.service.BackgroundService
import org.nmochizuki.scalaexample002.service.BackgroundService.LocalBinder


class MainActivity extends AppCompatActivity {

  val TAG = "MainActivity"

  val connection = new ServiceConnection {
    override def onServiceConnected(componentName: ComponentName, iBinder: IBinder): Unit = {
      Log.d(TAG, "onServiceConnected")
      val binder = iBinder.asInstanceOf[LocalBinder]
      service = Some(binder.service)
      isBound = true
    }
    override def onServiceDisconnected(componentName: ComponentName): Unit = {
      Log.d(TAG, "onServiceDisconnected")
      isBound = false
    }
  }

  var service: Option[BackgroundService] = None
  var isBound = false

  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    Log.d(TAG, "onCreate")
    setContentView(R.layout.activity_main)

    requestPermissions()

    val tabLayout = findViewById(R.id.tabs).asInstanceOf[TabLayout]
    val viewPager = findViewById(R.id.pager).asInstanceOf[ViewPager]
    val pagerAdapter = new AppPagerAdapter(getSupportFragmentManager, getApplicationContext)

    viewPager.setAdapter(pagerAdapter)
    tabLayout.setupWithViewPager(viewPager)
    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      override def onTabSelected(tab: TabLayout.Tab): Unit = {
        Log.d(TAG, "onTabSelected")
      }

      override def onTabUnselected(tab: TabLayout.Tab): Unit = {
        Log.d(TAG, "onTabUnselected")
      }

      override def onTabReselected(tab: TabLayout.Tab): Unit = {
        Log.d(TAG, "onTabReselected")
      }
    })

    val inflater = LayoutInflater.from(this)
    Tabs.values.foreach { t =>
      val tab = tabLayout.getTabAt(t._1)
      val view = inflater.inflate(R.layout.custom_tab, null)
      val textView = view.findViewById(R.id.tab_title).asInstanceOf[TextView]
      textView.setText(pagerAdapter.getPageTitle(t._1))
      tab.setCustomView(view)
    }

  }

  override def onStart(): Unit = {
    Log.d(TAG, "onStart")
    super.onStart()
    val intent = new Intent(this, classOf[BackgroundService])
    bindService(intent, connection, Context.BIND_AUTO_CREATE)
  }

  override def onResume(): Unit = {
    Log.d(TAG, "onResume")
    super.onResume()
  }

  override def onDestroy(): Unit = {
    super.onDestroy()
    if (isBound) {
      unbindService(connection)
      isBound = false
    }
  }

  private def requestPermissions(): Unit = {
    val permitRecord  = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
    val permitStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    if (permitRecord != PackageManager.PERMISSION_GRANTED
        || permitStorage != PackageManager.PERMISSION_GRANTED) {
      if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
        new ConfirmationDialog().show(getFragmentManager, "dialog")
      } else {
        requestPermissions(ConfirmationDialog.permissions, ConfirmationDialog.REQUEST_PERMISSION)
      }
    }
  }

  /**
    * マイク, ストレージへのアクセスの許可を確認するダイアログ
    */
  class ConfirmationDialog extends DialogFragment {

    val REQUEST_PERMISSION = 1
    val permissions: Array[String] = Array(
      Manifest.permission.RECORD_AUDIO,
      Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override def onCreateDialog(savedInstanceState: Bundle): Dialog = {
      val parent: Fragment = getParentFragment
      new AlertDialog.Builder(getActivity)
        .setMessage("ストレージへのアクセスの許可")
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
          override def onClick(dialog: DialogInterface, which: Int) {
            requestPermissions(permissions, REQUEST_PERMISSION)
          }
        })
        .setNegativeButton(android.R.string.cancel,
          new DialogInterface.OnClickListener() {
            override def onClick(dialog: DialogInterface, which: Int) {
              val activity = parent.getActivity
              if (activity != null) {
                activity.finish()
              }
            }
          })
        .create()
    }

  }

  object ConfirmationDialog {
    val dialog: ConfirmationDialog = new ConfirmationDialog()
    val REQUEST_PERMISSION = dialog.REQUEST_PERMISSION
    val permissions: Array[String] = dialog.permissions
  }

}
