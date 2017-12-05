package org.nmochizuki.scalaexample002.fragment

import android.view.{LayoutInflater, View, ViewGroup}
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View.OnClickListener
import android.widget.{Button, ProgressBar}
import org.nmochizuki.scalaexample002.R

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class App1Fragment extends Fragment {

  val TAG = "App1Fragment"

  private def updateProgressBar(view: View, progress: Int = 0) : Unit = {
    val progressBar = view.findViewById(R.id.progress_bar).asInstanceOf[ProgressBar]
    Log.d(TAG, s"progress: $progress")
    if (progress <= 1000) {
      progressBar.setProgress(progress + 10)
      Thread.sleep(10)
      updateProgressBar(view, progress + 10)
    }
  }

  override def onAttach(context: Context): Unit = {
    super.onAttach(context)
    Log.d(TAG, "onAttach")
  }

  override def onCreateView(inflater: LayoutInflater,
                            container: ViewGroup,
                            savedInstanceState: Bundle): View = {
    super.onCreateView(inflater, container, savedInstanceState)
    Log.d(TAG, "onCreateView")

    val view = inflater.inflate(R.layout.fragment_app1, container, false)

    val playButton = view.findViewById(R.id.button_play).asInstanceOf[Button]
    playButton.setOnClickListener(new OnClickListener {
      override def onClick(view: View): Unit = {
        Log.d(TAG, "onClick")
        Future {
          updateProgressBar(view)
        }
      }
    })
    view
  }

  override def onDetach(): Unit = {
    super.onDetach()
    Log.d(TAG, "onDetach")

  }

}
