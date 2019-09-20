package com.dmb.recipeapp.fragments;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmb.recipeapp.ApplicationContext;
import com.dmb.recipeapp.MainActivity;
import com.dmb.recipeapp.R;
import com.dmb.recipeapp.models.Category;
import com.dmb.recipeapp.models.Recipe;
import com.dmb.recipeapp.settings.AppConstants;
import com.dmb.recipeapp.utils.CommonUtils;
import com.huewu.pla.lib.MultiColumnListView;
import com.huewu.pla.lib.internal.PLA_AbsListView;
import com.huewu.pla.lib.internal.PLA_AdapterView;
import com.huewu.pla.lib.internal.PLA_AdapterView.OnItemClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Fragment for the main screen
 * @author dmb Team
 */
public class MainScreenFragment extends Fragment {

	public static final String TAG = MainScreenFragment.class.getSimpleName();
	private MultiColumnListView mListView;
	private ApplicationContext mApplicaitonContext;
	private View mHeaderView;

	private ImageLoader mImageLoader;

	private DisplayImageOptions mImageOptions;
	private ArrayList<Category> mCategories;

	/**
	 * Constructor
	 */
	public MainScreenFragment() {
		super();

		mImageLoader = ImageLoader.getInstance();
		mImageOptions = com.dmb.recipeapp.MainActivity.buildImageOptions(
				ImageScaleType.IN_SAMPLE_INT, true, true, 0, 0, 0);

	}

	/**
	 * Creates new instance of the fragment
	 * 
	 * @param appContext
	 *            application context
	 * @return
	 */
	public static MainScreenFragment newInstance(ApplicationContext appContext) {

		MainScreenFragment fragment = new MainScreenFragment();
		fragment.mApplicaitonContext = appContext;
		return fragment;
	}

	/**
	 * Called to have the fragment instantiate its user interface view. This is
	 * optional, and non-graphical fragments can return null (which is the
	 * default implementation). This will be called between onCreate(Bundle) and
	 * onActivityCreated(Bundle).
	 * 
	 * @param inflater
	 *            The LayoutInflater object that can be used to inflate any
	 *            views in the fragment,
	 * @param container
	 *            If non-null, this is the parent view that the fragment's UI
	 *            should be attached to. The fragment should not add the view
	 *            itself, but this can be used to generate the LayoutParams of
	 *            the view.
	 * @paramsavedInstanceState If non-null, this fragment is being
	 *                          re-constructed from a previous saved state as
	 *                          given here.
	 * 
	 * @return Return the View for the fragment's UI, or null.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main_screen, container,
				false);

		mListView = (MultiColumnListView) view.findViewById(android.R.id.list);

		mHeaderView = inflater.inflate(R.layout.list_header_main, container,
				false);

		renderHeaderView();

		mListView.addHeaderView(mHeaderView);

		mCategories = new ArrayList<Category>(0);

		if (mApplicaitonContext.getParsedApplicationSettings().getCategories()
				.size() > 1) {

			for (int i = 1; i < mApplicaitonContext
					.getParsedApplicationSettings().getCategories().size(); i++) {

				mCategories.add(mApplicaitonContext
						.getParsedApplicationSettings().getCategories().get(i));
			}
		}

		mCategories.add(CommonUtils
				.getFavoriteCategory((MainActivity) getActivity()));

		mListView.setAdapter(new MainFragmentListAdapter(getActivity(),
				fixCategoriesForAdapter(mCategories)));

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(PLA_AdapterView<?> parent, View view,
					int position, long id) {

				if (position == 0) {
					((MainActivity) getActivity()).showListScreen(
							(mApplicaitonContext.getParsedApplicationSettings()
									.getCategories().get(0).getRecipes()),
							true, false, true, false, mApplicaitonContext
									.getParsedApplicationSettings()
									.getCategories().get(0).getName());

					((MainActivity) getActivity())
							.setCorrectDrawerMenuItem((mApplicaitonContext
									.getParsedApplicationSettings()
									.getCategories().get(0).getName()));
				} else {
					((MainActivity) getActivity())
							.setCorrectDrawerMenuItem(mCategories.get(
									position - 1).getName());

					((MainActivity) getActivity()).showListScreen(mCategories
							.get(position - 1).getRecipes(), true, false, true, false,
							mCategories.get(position - 1).getName());
				}
			}
		});
		
		((MainActivity) getActivity()).deselectDrawerMenuItem();

		return view;
	}
	
	/**
	 * Fixes the categories used for the left menu
	 * 
	 * @param categories
	 *            all categories
	 * @return all categories without Favorites
	 */
	private ArrayList<Category> fixCategoriesForAdapter(ArrayList<Category> categories) {
		ArrayList<Category> result = new ArrayList<Category>();
		for (int i = 0; i < categories.size(); i++) {
			if (!categories.get(i).getName().equals(AppConstants.CATEGORY_FAVORITE)) {
				result.add(categories.get(i));
			}
		}
		return result;
	}

	/**
	 * Renders the view of the Header
	 */
	private void renderHeaderView() {
		if (mHeaderView != null
				&& mApplicaitonContext.getParsedApplicationSettings()
						.getCategories().size() > 0) {
			// We need to have at least one recipe to show
			if (mHeaderView != null
					&& mApplicaitonContext.getParsedApplicationSettings()
							.getCategories().get(0).getRecipes().size() == 0) {
				return;
			}

			TextView categoryName = (TextView) mHeaderView
					.findViewById(R.id.headerCategoryName);

			categoryName.setText(mApplicaitonContext
					.getParsedApplicationSettings().getCategories().get(0)
					.getName());
			CommonUtils.setRobotoThinFont(getActivity(), categoryName);

			TextView categoryRecipeCount = (TextView) mHeaderView
					.findViewById(R.id.headerCategoryCount);
			categoryRecipeCount.setText(Integer.toString(mApplicaitonContext
					.getParsedApplicationSettings().getCategories().get(0)
					.getRecipes().size()));
			CommonUtils.setRobotoThinFont(getActivity(), categoryRecipeCount);

			ImageView mainHeaderImage = (ImageView) mHeaderView
					.findViewById(R.id.list_header_main_image);

			// TODO Need to be random
			Recipe currentRecipeToShowAsHeader = mApplicaitonContext
					.getParsedApplicationSettings().getCategories().get(0)
					.getRecipes().get(0);
			
			String photoUri;
			if (CommonUtils.isContentLocal()) {
				photoUri = "drawable://"
						+ getResources().getIdentifier(
								currentRecipeToShowAsHeader.getRecipePictureUriList().get(0),
								"drawable", getActivity().getPackageName());
			} else {
				photoUri = currentRecipeToShowAsHeader.getRecipePictureUriList().get(0);
			}
			mImageLoader.displayImage(photoUri, mainHeaderImage, mImageOptions);

			mHeaderView.setLayoutParams(new PLA_AbsListView.LayoutParams(
					PLA_AbsListView.LayoutParams.MATCH_PARENT,
					PLA_AbsListView.LayoutParams.WRAP_CONTENT));
		}
	}

	/**
	 * Adapter class for the Main screen categories
	 * 
	 * @author dmb Team
	 * 
	 */
	private class MainFragmentListAdapter extends BaseAdapter {

		private Context mContext;

		private ArrayList<Category> mCategories;

		public MainFragmentListAdapter(Context context,
				ArrayList<Category> categories) {
			this.mCategories = categories;
			this.mContext = context;

		}

		@Override
		public int getCount() {
			if (mCategories != null) {
				return mCategories.size();
			} else {
				return 0;
			}
		}

		@Override
		public Category getItem(int position) {
			if (mCategories != null && position < mCategories.size()) {
				return mCategories.get(position);
			} else {
				return null;
			}
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {

				LayoutInflater inflater = LayoutInflater.from(mContext);
				convertView = inflater.inflate(R.layout.list_item_category,
						parent, false);
			}

			Category category = getItem(position);
			
			if (!category.getName().equalsIgnoreCase(AppConstants.CATEGORY_FAVORITE)) {
				TextView categoryName = (TextView) convertView
						.findViewById(R.id.headerCategoryName);
				categoryName.setText(category.getName());

				CommonUtils.setRobotoThinFont(getActivity(), categoryName);

				TextView categoryCount = (TextView) convertView
						.findViewById(R.id.headerCategoryCount);
				categoryCount.setText(category.getRecipes().size() + "");

				CommonUtils.setRobotoThinFont(getActivity(), categoryCount);

				ImageView categoryPic = (ImageView) convertView
						.findViewById(R.id.headerImageView);

				String photoUri;
				if (CommonUtils.isContentLocal()) {
					photoUri = "drawable://"
							+ mContext.getResources().getIdentifier(
									category.getRecipes().get(0).getRecipePictureUriList().get(0),
									"drawable", mContext.getPackageName());
				} else {
					photoUri = category.getRecipes().get(0).getRecipePictureUriList().get(0);
				}
				mImageLoader.displayImage(photoUri, categoryPic, mImageOptions);
			}

			

			return convertView;
		}
	}
}
