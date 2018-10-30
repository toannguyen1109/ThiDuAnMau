package com.thi.poly.thi;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.thi.poly.thi.adapter.Adapter;
import com.thi.poly.thi.listener.OnClick;
import com.thi.poly.thi.listener.OnDelete;
import com.thi.poly.thi.model.Phone;
import com.thi.poly.thi.sqlite.DAO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClick, OnDelete,DatePickerDialog.OnDateSetListener {
    Adapter adapter;
    DAO dao;
    List<Phone> phoneList;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private RecyclerView lvList;
    private EditText edMa;
    private EditText edTen;
    private EditText edGia;
    String id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edMa = findViewById(R.id.edMa);
        edTen = findViewById(R.id.edNgayMua);

        lvList = findViewById(R.id.lvList);
        dao = new DAO(this);
        phoneList = dao.getAll();
        adapter = new Adapter(phoneList, this, this);
        lvList.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        lvList.setLayoutManager(manager);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }

    private void setDate(final Calendar calendar) {
        edTen.setText(sdf.format(calendar.getTime()));
    }



    public static class DatePickerFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);
        }
    }

    public void datePicker(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(), "date");
    }

    @Override
    public void onClick(int position) {
        String name = phoneList.get(position).getName();
        id = phoneList.get(position).getID();



        edTen.setText(name);
        edMa.setText(id);
    }

    @Override
    public void onDelete(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Bạn có muốn xóa không ?");

        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long result = dao.delete(phoneList.get(position).getID());
                if (result > 0) {
                    phoneList = dao.getAll();
                    adapter.changeDataset(phoneList);
                    Toast.makeText(MainActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void them(View view) {
        String name = edTen.getText().toString().trim();

        String id = edMa.getText().toString().trim();

        if (name.isEmpty()  || id.isEmpty()) {
            if (name.isEmpty())
                edTen.setError("Không được để trống");

            if (id.isEmpty())
                edMa.setError("Không được để trống");
//        } else if (id.length() > 6 || Double.parseDouble(gia) <= 0 || name.length() < 2||Double.parseDouble(gia) >=5000000||name.length() >10) {
//            if (id.length() > 6)
//                edMa.setError("Mã không được lớn hơn 6 kí tự");
//            if (Double.parseDouble(gia) <= 0)
//                edGia.setError("Giá phải lớn hơn 0");
//            if (name.length() >10) {
//                edTen.setError("Tên không được lớn hơn 10 kí tự");
//            }
//            if (name.length() <2) {
//                edTen.setError("Tên phải có ít nhất 2 kí tự");
//            }
//            if(Double.parseDouble(gia) >=5000000)
//                edGia.setError("Giá tiền phải bé hơn 5 triệu");
        } else {
            Phone phone = dao.getByID(id);
            if (phone == null) {
                Phone phone1 = new Phone();
                long result = dao.insert(phone1);
                if (result > 0) {
                    phoneList = dao.getAll();
                    adapter.changeDataset(phoneList);
                    Toast.makeText(this, "Đã thêm", Toast.LENGTH_SHORT).show();
                    edMa.setText("");
                    edTen.setText("");

                } else {
                    Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            } else {
                edMa.setError("Mã sách đã tồn tại");
            }
        }
    }

    public void edit(View view) {
        String name = edMa.getText().toString().trim();
        String gia = edTen.getText().toString().trim();

        if (name.isEmpty() || gia.isEmpty() || id == null) {
            if (name.isEmpty())
                edTen.setError("Không được để trống");
            if (gia.isEmpty())
                edMa.setError("Không được để trống");
            if (id == null)
                Toast.makeText(this, "Vui lòng chọn dòng cần sửa", Toast.LENGTH_SHORT).show();
        }
         else {
            if (edMa.getText().toString().trim().equals(id)) {
                Phone phone1 = new Phone();
                long result = dao.update(phone1);
                if (result > 0) {
                    phoneList = dao.getAll();
                    adapter.changeDataset(phoneList);
                    Toast.makeText(this, "Đã sửa", Toast.LENGTH_SHORT).show();
                    edMa.setText("");
                    edTen.setText("");

                } else {
                    Toast.makeText(this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                }
            } else {
                edMa.setError("Không thể thay đổi mã");
            }
        }
    }
}
