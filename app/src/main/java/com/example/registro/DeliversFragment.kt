package com.example.registro

import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.registro.Database.UserRepository
import com.example.registro.Local.UserDataSource
import com.example.registro.Local.UserDatabase
import com.example.registro.Model.User
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_delivers.*
import io.reactivex.ObservableOnSubscribe
import io.reactivex.functions.Action
import java.util.*
import kotlin.collections.ArrayList
/**
 * A simple [Fragment] subclass.
 */
class DeliversFragment : Fragment() {

    lateinit var adapter:ArrayAdapter<*>
    var userList:MutableList<User> = ArrayList ()

    private var compositeDisposable:CompositeDisposable?=null
    private var userRepository:UserRepository?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    { // Inflate the layout for this fragment
        val viewss = inflater.inflate(R.layout.fragment_delivers, container, false)

        compositeDisposable = CompositeDisposable()
        adapter = ArrayAdapter(activity!!, android.R.layout.simple_list_item_1,userList)
        nel!!.adapter = adapter


        val userDatabase = UserDatabase.getInstance(this.activity!!)
        userRepository = UserRepository.getInstance(UserDataSource.getInstance(userDatabase.userDAO()))

        loadData()
        fab_add.setOnClickListener {
            val disposable = Observable.create(ObservableOnSubscribe<Any> {e->
                val user = User()
                user.name= "EDMTDev"
                user.email = UUID.randomUUID().toString()+"@gmail.com"
                userRepository!!.insertUser()
                e.onComplete()
            })

                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(io.reactivex.functions.Consumer{

                },
                    io.reactivex.functions.Consumer{ throwable-> Toast.makeText(this.activity!!,""+throwable.message, Toast.LENGTH_SHORT)
                        .show()

                    },
                    Action { loadData() })
            compositeDisposable!!.addAll(disposable)
        }

        return viewss
    }

    private fun loadData(){
        val disposable = userRepository!!.allUsers
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({users->onGetAllUserSucces(users)})
            {
                throwable -> Toast.makeText(this.activity!!,""+throwable.message,Toast.LENGTH_SHORT)
                .show()
            }
        compositeDisposable!!.add(disposable)
    }

    private fun onGetAllUserSucces(users: List<User>?) {
        userList.clear()
        userList.addAll(users!!)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_clear,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override  fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.fragment_Clear -> deleteAllUsers()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUsers() {
        val disposable = Observable.create(ObservableOnSubscribe<Any> {e->
            userRepository!!.deleteAllUsers()
            e.onComplete()
        })

            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(io.reactivex.functions.Consumer{ },
                io.reactivex.functions.Consumer{
                        throwable-> Toast.makeText(this.activity!!,""+throwable.message, Toast.LENGTH_SHORT)
                    .show()

                },
                Action { loadData() })
        compositeDisposable!!.addAll(disposable)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val  info = menuInfo as AdapterView.AdapterContextMenuInfo
        menu.setHeaderTitle("Select Action")
        menu.add(Menu.NONE,0,Menu.NONE,"UPDATE")

    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val  user = userList [info.position]
        when (item.itemId){
            0
            ->
            {
                val edtName = EditText (getActivity())
                edtName.setText(user.name)
                edtName.hint = "Enter your name"
                AlertDialog. Builder(this!!.activity!!)
                    .setTitle("Edit")
                    .setMessage("Edit user name")
                    .setView(edtName)
                    .setPositiveButton(android.R.string.ok,
                        DialogInterface.OnClickListener { dialog, which ->
                        if (TextUtils.isEmpty(edtName.text.toString()))
                            return@OnClickListener
                        else
                        {
                            user.name = edtName.text.toString()
                            updateUser(user)
                        }
                    }).setNegativeButton(android.R.string.cancel){
                            dialog, which -> dialog.dismiss()
                    }.create()
                    .show()
            }
            1
            ->
            {
                AlertDialog.Builder(this.activity!!)
                    .setMessage("quieres eliminar"+user.name)
                    .setPositiveButton(android.R.string.ok,
                        DialogInterface.OnClickListener { dialog, which ->
                        deleteUser(user)
                    }) .setNegativeButton(android.R.string.cancel){
                            dialog, which -> dialog.dismiss()
                    }.create()
                    .show()
            }
        }
        return true
    }


    private fun deleteUser(user: User) {
        val disposable = Observable.create(ObservableOnSubscribe<Any> {e->
            userRepository!!.deleteUser(user)
            e.onComplete()
        })

            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(io.reactivex.functions.Consumer{

            },
                io.reactivex.functions.Consumer{
                        throwable-> Toast.makeText(this.activity!!,""+throwable.message, Toast.LENGTH_SHORT)
                    .show()

                },
                Action { loadData() })
        compositeDisposable!!.addAll(disposable)
    }

    private fun updateUser(user: User) {
        val disposable = Observable.create(ObservableOnSubscribe<Any> {e->
            userRepository!!.updateUser(user)
            e.onComplete()
        })

            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(io.reactivex.functions.Consumer{

            },
                io.reactivex.functions.Consumer{
                        throwable-> Toast.makeText(this.activity!!,""+throwable.message, Toast.LENGTH_SHORT)
                    .show()

                },
                Action { loadData() })
        compositeDisposable!!.addAll(disposable)
    }
    override fun onDestroy() {
        compositeDisposable!!.clear()
        super.onDestroy()
    }
}
