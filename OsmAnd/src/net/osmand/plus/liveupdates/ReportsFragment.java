package net.osmand.plus.liveupdates;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import net.osmand.plus.IconsCache;
import net.osmand.plus.R;
import net.osmand.plus.base.BaseOsmAndFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReportsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReportsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportsFragment extends BaseOsmAndFragment {
	public static final String TITLE = "Report";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_reports, container, false);
		Spinner montReportsSpinner = (Spinner) view.findViewById(R.id.montReportsSpinner);
		MonthsForReportsAdapter monthsForReportsAdapter = new MonthsForReportsAdapter(getActivity());
		montReportsSpinner.setAdapter(monthsForReportsAdapter);

		Spinner regionReportsSpinner = (Spinner) view.findViewById(R.id.regionReportsSpinner);
		ArrayAdapter<String> regionsForReportsAdapter =
				new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
						new String[] {"Worldwide"});
		regionsForReportsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		regionReportsSpinner.setAdapter(regionsForReportsAdapter);

		IconsCache iconsCache = getMyApplication().getIconsCache();
		setThemedDrawable(view, R.id.calendarImageView, R.drawable.ic_action_data);
		setThemedDrawable(view, R.id.regionIconImageView, R.drawable.ic_world_globe_dark);
		setThemedDrawable(view, R.id.numberOfContributorsIcon, R.drawable.ic_group);
		setThemedDrawable(view, R.id.numberOfEditsIcon, R.drawable.ic_group);
		return view;
	}

	private static class MonthsForReportsAdapter extends ArrayAdapter<String> {
		private static final SimpleDateFormat queryFormat = new SimpleDateFormat("yyyy-MM", Locale.US);
		@SuppressLint("SimpleDateFormat")
		private static final SimpleDateFormat humanFormat = new SimpleDateFormat("MMMM yyyy");

		ArrayList<String> queryString = new ArrayList<>();

		public MonthsForReportsAdapter(Context context) {
			super(context, android.R.layout.simple_spinner_item);
			Calendar startDate = Calendar.getInstance();
			startDate.set(Calendar.MONTH, Calendar.JUNE);
			startDate.set(Calendar.YEAR, 2015);
			Calendar endDate = Calendar.getInstance();
			endDate.set(Calendar.DAY_OF_MONTH, endDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			endDate.set(Calendar.HOUR_OF_DAY, endDate.getActualMaximum(Calendar.HOUR_OF_DAY));
			while (startDate.before(endDate)) {
				queryString.add(queryFormat.format(startDate.getTime()));
				add(humanFormat.format(startDate.getTime()));
				startDate.add(Calendar.MONTH, 1);
			}
			setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		}

		public String getQueryString(int position) {
			return queryString.get(position);
		}
	}

//	public static class GetJsonAsyncTask<Protocol> extends AsyncTask<String, Void, Protocol> {
//		private static final Log LOG = PlatformUtil.getLog(GetJsonAsyncTask.class);
//		private final Class<Protocol> protocolClass;
//
//		public GetJsonAsyncTask(Class<Protocol> protocolClass) {
//			this.protocolClass = protocolClass;
//		}
//
//		@Override
//		protected Protocol doInBackground(String... params) {
//			StringBuilder response = new StringBuilder();
//			String error = NetworkUtils.sendGetRequest(params[0], null, response);
//			if (error != null) {
//				return
//			}
//			LOG.error(error);
//			return null;
//		}
//	}
}
