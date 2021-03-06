package com.example.user.xsgl;


import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedHashSet;
import java.util.Set;

import static com.example.user.xsgl.R.id.btn_add_do;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdataInfoFragment extends Fragment {


    EditText edit_updata_no,edit_updata_name;
    RadioButton radio_updata_b,radio_updata_g;
    Button btn_updata_do;
    ListView lvLike;
    String Gender="",Like="";
    Set set;
    Helper helper;
    Cursor cursor;
    TextView tvChoose;

    public UpdataInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_updata_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_updata_do=(Button)getActivity().findViewById(R.id.btn_updata_do);
        edit_updata_no=(EditText)getActivity().findViewById(R.id.edit_updata_no);
        edit_updata_name=(EditText)getActivity().findViewById(R.id.edit_updata_name);
        radio_updata_b = (RadioButton)getActivity().findViewById(R.id.radio_updata_b);
        radio_updata_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radio_updata_b.isChecked()) {
                    Toast.makeText(getActivity().getApplicationContext(),"男",Toast.LENGTH_SHORT).show();
                    Gender="男";
                }
            }
        });
        radio_updata_g = (RadioButton)getActivity().findViewById(R.id.radio_updata_g);
        radio_updata_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radio_updata_g.isChecked()) {
                    Toast.makeText(getActivity().getApplicationContext(),"女",Toast.LENGTH_SHORT).show();
                    Gender="女";
                }
            }
        });
        tvChoose=(TextView)getActivity().findViewById(R.id.tvChoose);
        tvChoose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                set=new LinkedHashSet();
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                lvLike=new ListView(getActivity());
                lvLike.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

                if(radio_updata_b.isChecked()) {
                    ArrayAdapter adapter=ArrayAdapter.createFromResource(getActivity(),
                            R.array.likeNan,android.R.layout.select_dialog_multichoice);
                    lvLike.setAdapter(adapter);
                }
                if(radio_updata_g.isChecked()) {
                    ArrayAdapter adapter=ArrayAdapter.createFromResource(getActivity(),
                            R.array.likeNv,android.R.layout.select_dialog_multichoice);
                    lvLike.setAdapter(adapter);
                }
                builder.setView(lvLike);
                lvLike.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        set.add(parent.getItemAtPosition(position).toString());
                    }
                });
                builder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Like="";
                        for(Object obj:set){
                            Like+=(String)obj+" ";
                        }
                        Toast.makeText(getActivity(),"爱好："+Like,Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        btn_updata_do.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                helper=new Helper(getActivity());
                Student s=new Student();
                s.Sno=edit_updata_no.getText().toString();
                s.Name=edit_updata_name.getText().toString();
                s.Gender=Gender;
                s.Like=Like;
                helper.updateStudent(s);
                Toast.makeText(getActivity(),"更新成功",Toast.LENGTH_LONG).show();
                DataFresh();
            }
        });
    }
    void DataFresh(){
        helper = new Helper(getActivity());
        cursor = helper.queryStudent();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.info, cursor,
                new String[]{"Sno", "Name", "Gender", "Like"},
                new int[]{R.id.tvSno, R.id.tvName, R.id.tvGender, R.id.tvLike});
        Main2Activity.list_info.setAdapter(adapter);
    }
}
