package com.guntur.uasguntur211057011

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.NotificationCompat
import com.guntur.uasguntur211057011.R

class CustomAdapter  (context: Context, arrayListDetails: ArrayList<Model>) : BaseAdapter(){
        private val layoutInflater: LayoutInflater
        private val arrayListDetails: ArrayList<Model>

        init {
            this.layoutInflater = LayoutInflater.from(context)
            this.arrayListDetails = arrayListDetails
        }

        override fun getCount(): Int {
            return arrayListDetails.size
        }

        override fun getItem(position: Int): Any {
            return arrayListDetails.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            val view: View?
            val listRowHolder: ListRowHolder
            if (convertView == null){
                view = this.layoutInflater.inflate(R.layout.todo_row_item, parent, false)
                listRowHolder = ListRowHolder(view)
                view.tag = listRowHolder
            } else {
                view = convertView
                listRowHolder = view.tag as ListRowHolder
            }
            listRowHolder.tvuser.text = arrayListDetails.get(position).userId
            listRowHolder.tvuserid.text = arrayListDetails.get(position).id
            listRowHolder.tvtitle.text = arrayListDetails.get(position).title
            listRowHolder.tvstatus.text = arrayListDetails.get(position).completed.toString()
            return view
        }

        public class ListRowHolder(row: View?){
            public val tvuserid: TextView
            public val tvuser: TextView
            public val tvtitle: TextView
            public val tvstatus: TextView
            public val linearLayout: LinearLayout
            init {
                this.tvuser = row?.findViewById<TextView>(R.id.tv_id_activity) as TextView
                this.tvuserid = row?.findViewById<TextView>(R.id.tv_id) as TextView
                this.tvstatus = row?.findViewById<TextView>(R.id.tv_status) as TextView
                this.tvtitle = row?.findViewById<TextView>(R.id.tv_title) as TextView
                this.linearLayout =
                    row?.findViewById<LinearLayout>(R.id.linearLayout) as LinearLayout
            }

        }
    }