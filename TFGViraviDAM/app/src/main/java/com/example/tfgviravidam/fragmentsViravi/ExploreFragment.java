package com.example.tfgviravidam.fragmentsViravi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tfgviravidam.Adapter.CategoryAdapter;
import com.example.tfgviravidam.Adapter.PopularAdapter;
import com.example.tfgviravidam.DAO.Categorias;
import com.example.tfgviravidam.DAO.Evento;
import com.example.tfgviravidam.R;

import java.util.ArrayList;


public class ExploreFragment extends Fragment {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewCategory;
    private RecyclerView recyclerViewPopular;
    private RecyclerView d;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        recyclerViewCategory(view);
        recyclerViewPopular(view);


        return view;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void recyclerViewCategory(View view) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategory=view.findViewById(R.id.viewCategory);
        recyclerViewCategory.setLayoutManager(linearLayoutManager);

        ArrayList<Categorias> categoria = new ArrayList<>();
        categoria.add(new Categorias("Fiesta","copa-de-champan","yellow_pastel"));
        categoria.add(new Categorias("Turismo","museo_britanico","blue_pastel"));
        categoria.add(new Categorias("Actividades","parque","red_pastel"));
        categoria.add(new Categorias("Viajes","passport","orange_pastel"));
        categoria.add(new Categorias("Gastronomia","restaurant","purple_pastel"));
        categoria.add(new Categorias("Deportes","sports","brown_pastel"));

        adapter=new CategoryAdapter(categoria);
        recyclerViewCategory.setAdapter(adapter);

    }
    private void recyclerViewPopular(View view) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopular=view.findViewById(R.id.viewPopuPlans);
        recyclerViewPopular.setLayoutManager(linearLayoutManager);

        ArrayList<Evento> eventos = new ArrayList<>();
        eventos.add(new Evento("Viaje a Thailandia","Viajes", "Viajes", "12/03/2023", "25/03/2023"));
        eventos.add(new Evento("Viaje a Thailandia","Viajes", "Viajes", "12/03/2023", "25/03/2023"));
        eventos.add(new Evento("Viaje a Thailandia","Viajes", "Viajes", "12/03/2023", "25/03/2023"));
        Log.i("e",eventos.toString());


        adapter=new PopularAdapter(eventos);
        recyclerViewPopular.setAdapter(adapter);

    }
}