package totabraz.com.socorromovel.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import totabraz.com.socorromovel.R;
import totabraz.com.socorromovel.activities.informations.AnonymousReportAlertActivity;
import totabraz.com.socorromovel.fragments.ListCallsFragment;
import totabraz.com.socorromovel.fragments.LoginFragment;
import totabraz.com.socorromovel.fragments.ProfileFragment;
import totabraz.com.socorromovel.fragments.SearchImeiFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        openHomeFrag();
    }

    private void openHomeFrag() {
        getSupportActionBar().setTitle("Segurança Digital");
        this.fragmentManager = getSupportFragmentManager();
        this.fragmentTransaction = fragmentManager.beginTransaction();
        this.fragmentTransaction.replace(R.id.flFragmentArea, SearchImeiFragment.newInstance());
        this.fragmentTransaction.addToBackStack(null);
        this.fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        int indice_frag_home = fragmentManager.getBackStackEntryCount();
        if (indice_frag_home == 1) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Deseja realmente sair?")
                    .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            openHomeFrag();
                        }
                    })
                    .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })

                    .setIcon(R.drawable.ic_warning)
                    .show();

        } else if (indice_frag_home > 1) {
            fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(1).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            this.openHomeFrag();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        this.fragmentManager = getSupportFragmentManager();
        this.fragmentTransaction = fragmentManager.beginTransaction();

        if (id == R.id.nav_verifyPhone) {
            getSupportActionBar().setTitle("Buscar por IMEI");
            SearchImeiFragment searchImeiFragment = SearchImeiFragment.newInstance();
            this.fragmentTransaction.replace(R.id.flFragmentArea, searchImeiFragment);

        } else if (id == R.id.nav_addPhone) {
            if (mAuth.getCurrentUser() == null) {
                LoginFragment loginFragment = LoginFragment.newInstance();
                this.fragmentTransaction.replace(R.id.flFragmentArea, loginFragment);
            } else {
                ProfileFragment profileFragment = ProfileFragment.newInstance();
                this.fragmentTransaction.replace(R.id.flFragmentArea, profileFragment);
            }

        } else if (id == R.id.nav_emergencyCall) {
            getSupportActionBar().setTitle("Chamadas de Emergência");
            ListCallsFragment listCallsFragment = ListCallsFragment.newInstance();
            this.fragmentTransaction.replace(R.id.flFragmentArea, listCallsFragment);

        } else if (id == R.id.nav_report) {
            startActivity(new Intent(getApplicationContext(), AnonymousReportAlertActivity.class));
        }

        this.fragmentTransaction.addToBackStack(null);
        this.fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
