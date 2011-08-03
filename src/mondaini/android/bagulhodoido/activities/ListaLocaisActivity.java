package mondaini.android.bagulhodoido.activities;

import java.util.List;

import mondaini.android.bagulhodoido.R;
import mondaini.android.bagulhodoido.adapters.LocalAdapter;
import mondaini.android.bagulhodoido.db.DBAdapter;
import mondaini.android.bagulhodoido.db.Data;
import mondaini.android.bagulhodoido.model.Local;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ListaLocaisActivity extends Activity {
	private ListView lvLocais;
	public List<Local> locais;
	private LocalAdapter localAdapter;
	private DBAdapter mDb;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.local_list);
		this.lvLocais = (ListView) findViewById(R.id.listLocais);
		mDb = new DBAdapter(this);
		new GetLocais().execute();
	}

	class GetLocais extends AsyncTask<Void, Void, Boolean>{

		@Override
		protected Boolean doInBackground(Void... arg) {
			mDb.open();
			new Data(ListaLocaisActivity.this).insertInitialData();
			ListaLocaisActivity.this.locais = mDb.getLocais();
			if (locais != null && locais.size() > 0){
				return true;
			}
			else{
				return false;
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			Toast.makeText(ListaLocaisActivity.this, "Lista de notícias atualizada com sucesso!", Toast.LENGTH_LONG).show();
			if (result == true){			
				localAdapter = new LocalAdapter(ListaLocaisActivity.this, locais);
				lvLocais.setOnItemClickListener(new OnClickListaLocais());
				lvLocais.setAdapter(localAdapter);
			}else{
				Toast.makeText(ListaLocaisActivity.this, "Ocorreu um erro ao buscar a lista de locais\n:-(", Toast.LENGTH_LONG).show();
			}
			mDb.close();
		}

	}

	class OnClickListaLocais implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {		
			Local local = ListaLocaisActivity.this.locais.get(position);
			
			String endereco = local.endereco;			
			endereco = endereco.replace(' ', '+');

			String GEO_URI = "geo:0,0?q=";
			Uri uriGeo = Uri.parse(GEO_URI + endereco);

			Intent it = new Intent(android.content.Intent.ACTION_VIEW, uriGeo);
			startActivity(it);		
		}
		
	}
}
