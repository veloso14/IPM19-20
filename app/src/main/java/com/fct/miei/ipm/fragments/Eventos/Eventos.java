package com.fct.miei.ipm.fragments.Eventos;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brutal.ninjas.hackaton19.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;


public class Eventos extends Fragment implements CalendarioAdapter.eventoListener {

    private TextView monthText;
    private CompactCalendarView calendar;
    private LinearLayout linearContainer;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private View view;
    private Dialog myDialog;
    private ArrayList<Event> itemsData = new ArrayList<>();
    private boolean sc = false;
    private String eventdatabase = "";
    private SimpleDateFormat df = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    private RecyclerView.OnScrollListener on_scroll = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            try {
                int pos = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                long time = itemsData.get(pos).getTimeInMillis();
                if (time > 0 && !calendar.isAnimating() && !sc) {
                    calendar.setCurrentDate(new Date(time));
                    setMonthText();
                }
            } catch (Exception ignored) {

            }
        }


    };

    public Eventos() {
        // Required empty public constructor
    }

    /**
     * Create a new instance of the fragment
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_calendario, container, false);

        // create the popup window
        myDialog = new Dialog(getContext());

        //Calendario
        linearContainer = view.findViewById(R.id.linear);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        monthText = view.findViewById(R.id.monthText);

        calendar = view.findViewById(R.id.calendar);
        calendar.setUseThreeLetterAbbreviation(true);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setEventIndicatorStyle(3);
        calendar.shouldDrawIndicatorsBelowSelectedDays(true);
        calendar.setCurrentDayIndicatorStyle(2);
        calendar.setCurrentSelectedDayIndicatorStyle(2);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Log.d("Clicked" , "Cliou em " + position);
                        try {
                            JSONArray evento = new JSONArray(eventdatabase);
                            Log.d("Clicked" , evento.get(position).toString());
                            ShowPopupEventoDetails( view.findViewById(android.R.id.content) , evento.getJSONObject(position) , position);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    Log.d("Scrool", "1");
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    Log.d("Scrool", "2");
                    sc = false;
                } else {
                    Log.d("Scrool", "3");
                    sc = false;

                }
            }
        });

        recyclerView.addOnScrollListener(on_scroll);


        calendar.setAnimationListener(new CompactCalendarView.CompactCalendarAnimationListener() {
            @Override
            public void onOpened() {

                refresh();
                Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
                if (recyclerView != null) {
                    recyclerView.startAnimation(fadeIn);
                    recyclerView.setAlpha(1);
                }
            }

            @Override
            public void onClosed() {

            }
        });

        calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {

            @Override
            public void onDayClick(Date dateClicked) {
                sc = true;
                scrollToDate(dateClicked);
                // 21/11/2019
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String parsed = formatter.format(dateClicked);
                Log.d("DATE" ,parsed);
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("DATECLICKED", MODE_PRIVATE).edit();
                editor.putString("DATECLICKED", parsed);
                editor.apply();
                ShowPopup(view.findViewById(android.R.id.content));
            }


            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setMonthText();
                sc = true;
                scrollToDate(firstDayOfNewMonth);
            }
        });


        setMonthText();
        getEvents();

        return view;

    }


    private void getEvents() {
        Log.d("Eventos", "Start");
        SharedPreferences settings = getContext().getSharedPreferences("Eventos", 0);
        String[] playlists = settings.getString("Eventos", "").split(";");
        String eventosCriados = ",";
        for( int i = 0 ; i < playlists.length ; i++){
            eventosCriados = eventosCriados + " " + playlists[i] ;
        }
        this.eventdatabase = "[\n" +
                "  {\n" +
                "    \"time\": \"21/11/2019\",\n" +
                "    \"color\": \"#08A5CB\",\n" +
                "    \"name\": \"Evento Sessão de estudo\",\n" +
                "    \"numPessoas\": \"" + "5" + "\",\n" +
                "    \"local\": \"" + "Biblioteca FCT-UNL" + "\",\n" +
                "    \"fim\": \"" + "16:30" + "\",\n" +
                "    \"inicio\": \"" + "14:00" + "\",\n" +
                "    \"allday\": false\n" +
                "  }\n" +
                eventosCriados +
                "  {\n" +
                "    \"time\": \"19/08/2019\",\n" +
                "    \"color\": \"#08A5CB\",\n" +
                "    \"name\": \"Evento Sessão de estudo\",\n" +
                "    \"numPessoas\": \"" + "5" + "\",\n" +
                "    \"local\": \"" + "NEEC-FCT" + "\",\n" +
                "    \"fim\": \"" + "22:30" + "\",\n" +
                "    \"inicio\": \"" + "18:00" + "\",\n" +
                "    \"allday\": false\n" +
                "  },\n" +
                "  {\n" +
                "    \"time\": \"26/08/2019\",\n" +
                "    \"color\": \"#095280\",\n" +
                "    \"name\": \"Evento Sessão de estudo\",\n" +
                "    \"numPessoas\": \"" + "5" + "\",\n" +
                "    \"local\": \"" + "Biblioteca FCT-UNL" + "\",\n" +
                "    \"allday\": true\n" +
                "  }\n" +
                "]";


        itemsData.clear();

        itemsData.add(new Event(0, 0, "A carregar calendário..."));
        refreshAdapter();
        Log.d("Eventos", "Recv");
        //Log.d("Response is: ", response);
        try {
            JSONArray array = new JSONArray( this.eventdatabase);
            Log.d("length", "" + array.length());
            itemsData.clear();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                int color = Color.parseColor(obj.getString("color"));
                long time = dateFormat.parse(obj.getString("time")).getTime();
                String name = obj.getString("name");
                Event evt = new Event(color, time, name);
                itemsData.add(evt);
            }

        } catch (JSONException e) {
            Log.e("json", e.getMessage());

            itemsData.clear();

            itemsData.add(new Event(0, 0, "Ocorreu um erro a processar o calendário(1)."));
        } catch (ParseException e) {

            Log.e("json", e.getMessage());

            itemsData.clear();

            itemsData.add(new Event(0, 0, "Ocorreu um erro a processar o calendário(2)."));
            e.printStackTrace();
        } finally {

            Log.d("Eventos", "Added");
            refreshAdapter();
            refresh();
        }


        Log.d("Eventos", "Q");


    }

    public void ShowPopupEventoDetails(View v, JSONObject data, int position) {

        int size = 0;
        try {
            size = new JSONArray(eventdatabase).length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Não apagar os default
        if(position == 0 || (position == size -2) || (position == size -1) ){
            myDialog.setContentView(R.layout.popup_show_evento);
        }
        else {
            myDialog.setContentView(R.layout.popup_show_evento_delete);
            Button apagar  = myDialog.findViewById(R.id.apagar);
            apagar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        JSONArray jsonObject = new JSONArray(eventdatabase);

                        ArrayList<String> list = new ArrayList<String>();
                        JSONArray jsonArray = (JSONArray)jsonObject;
                        int len = jsonArray.length();
                        if (jsonArray != null) {
                            for (int i=0;i<len;i++){
                                list.add(jsonArray.get(i).toString());
                            }
                        }
                        //Remove the element from arraylist
                        list.remove(position);
                        list.remove(0);
                        list.remove(list.size() - 1);
                        list.remove(list.size() - 1);

                        //save
                        SharedPreferences settings = getContext().getSharedPreferences("Eventos", 0);
                        String[] playlists = new String[list.size()];
                        //copy
                        for(int i = 0 ; i < list.size()  ; i++){
                            if(i == list.size())
                                playlists[i] = list.get(i) ;
                            else
                                playlists[i] = list.get(i) + " , ";
                        }
                        SharedPreferences.Editor editor = settings.edit();
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < playlists.length; i++) {
                            sb.append(playlists[i]).append(";");
                        }
                        editor.putString("Eventos", sb.toString());
                        editor.commit();
                        recyclerView.refreshDrawableState();
                        getEvents();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        myDialog.dismiss();
                    }
                    myDialog.dismiss();
                }
            });
        }

        TextView txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });




        TextView titulo = myDialog.findViewById(R.id.titulo);
        TextView numpessoas = myDialog.findViewById(R.id.numpessoas);
        TextView horas = myDialog.findViewById(R.id.horas);
        TextView local = myDialog.findViewById(R.id.local);
        //Data
        try {
            if(data.getBoolean("allday")){
                horas.setText("O evento decorre o dia inteiro.");
            }
            else{
                horas.setText("Horas " + data.getString("inicio") + " às " +  data.getString("fim"));
            }
        } catch (JSONException e) {
            horas.setText("Horas não estão definido");
        }
        //Local
        try {
            local.setText(data.getString("local"));
        } catch (JSONException e) {
            local.setText("Não local não estão definido");
        }
        //Numero pessoas
        try {
            numpessoas.setText(data.getString("numPessoas"));
        } catch (JSONException e) {
            numpessoas.setText("Não número de pessoas não definido.");
        }
        //Titulo
        try {
            titulo.setText(data.getString("name"));
        } catch (JSONException e) {
            titulo.setText("Não definido.");
        }

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void ShowPopup(View v) {

        myDialog.setContentView(R.layout.popup_window_criar_evento);
        Button novoEvento = myDialog.findViewById(R.id.novoEvento);

        novoEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, new CriarEvento());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    private void refreshAdapter() {
        CalendarioAdapter adapter = new CalendarioAdapter(itemsData, this);
        recyclerView.setAdapter(adapter);
        calendar.removeAllEvents();
        calendar.addEvents(itemsData);
    }


    private void setMonthText() {

        if (monthText != null && calendar != null) {
            try {
                Date day = calendar.getFirstDayOfCurrentMonth();
                String string = df.format(day);
                monthText.setText(string);
            } catch (Exception e) {
                Log.e("monthText", e.getMessage());
            }
        }
    }

    private void scrollToDate(Date dateClicked) {
        if (getContext() == null)
            return;
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        Calendar date = new GregorianCalendar();
        date.setTimeInMillis(dateClicked.getTime());
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        int i;
        for (i = 0; i < itemsData.size(); i++) {
            if (itemsData.get(i).getTimeInMillis() >= date.getTimeInMillis())
                break;
        }
        smoothScroller.setTargetPosition(i);
        layoutManager.startSmoothScroll(smoothScroller);
    }

    /**
     * Refresh
     */
    void refresh() {
        Date date = new Date();
        if (calendar != null) {
            sc = true;
            calendar.setCurrentDate(date);
            scrollToDate(date);
            setMonthText();
        }

    }

    /**
     * Called when a fragment will be displayed
     */
    void willBeDisplayed() {
        // Do what you want here, for example animate the content
        if (linearContainer != null) {
            recyclerView.setAlpha(0);
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            linearContainer.startAnimation(fadeIn);
            if (calendar != null) {
                refresh();
                calendar.showCalendarWithAnimation();
            }
        }
    }

    /**
     * Called when a fragment will be hidden
     */
    void willBeHidden() {
        if (linearContainer != null) {
            Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
            linearContainer.startAnimation(fadeOut);

        }
    }

    @Override
    public void onEventoClick(int pos) {
        long time = itemsData.get(pos).getTimeInMillis();
        if (time > 0) {
            calendar.setCurrentDate(new Date(time));
            setMonthText();
        }
    }
}
