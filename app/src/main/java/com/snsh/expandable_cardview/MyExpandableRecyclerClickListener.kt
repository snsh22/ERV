package comm.saingaung.senghein.simplerecyclerview.uty.itf

interface MyExpandableRecyclerClickListener {
    fun onParentClicked(id: Int, text: String)

    fun onChildClicked(id: Int, text: String)
}