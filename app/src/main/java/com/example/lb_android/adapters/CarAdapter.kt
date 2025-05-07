package com.example.lb_android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lb_android.R
import com.example.lb_android.models.Car

class CarAdapter(private var carList: List<Car>) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val brandTextView: TextView = itemView.findViewById(R.id.textViewBrand)
        val modelTextView: TextView = itemView.findViewById(R.id.textViewModel)
        val yearTextView: TextView = itemView.findViewById(R.id.textViewYear)
        val descriptionTextView: TextView = itemView.findViewById(R.id.textViewDescription)
        val costTextView: TextView = itemView.findViewById(R.id.textViewCost)
        val photoImageView: ImageView = itemView.findViewById(R.id.imageViewPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_itemcar, parent, false)
        return CarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val currentCar = carList[position]
        holder.brandTextView.text = currentCar.brand
        holder.modelTextView.text = currentCar.model
        holder.yearTextView.text = currentCar.year.toString()
        holder.descriptionTextView.text = currentCar.description
        holder.costTextView.text = String.format("%.2f USD", currentCar.cost)
        val imageResource = holder.itemView.context.resources.getIdentifier(
            currentCar.photoName,
            "drawable",
            holder.itemView.context.packageName
        )
        holder.photoImageView.setImageResource(imageResource)
        holder.photoImageView.contentDescription = currentCar.description
    }

    override fun getItemCount() = carList.size

    fun updateData(newCarList: List<Car>) {
        carList = newCarList
        notifyDataSetChanged()
    }
}