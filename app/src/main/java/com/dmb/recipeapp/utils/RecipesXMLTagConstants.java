package com.dmb.recipeapp.utils;

/**
 * 
 * Recipe XML tag constants used for parsing the XML
 */
public final class RecipesXMLTagConstants {

	// Optional tag used for obtaining XML data from provided URL address. If
	// blank XML data is collected from the XML file provided in the assets
	// folder
	public static final String TAG_URL_SETTINGS = "";

	public static final String TAG_SETTINGS = "Settings";

	public static final String TAG_MAIN_SETTINGS = "MainSettings";
	public static final String TAG_LOGO_ACTIVE = "LogoActive";
	public static final String TAG_LOGO_INACTIVE = "LogoInactive";

	public static final String TAG_CATEGORY = "Category";
	public static final String TAG_APPETIZERS_LIST = "ApetizersList";
	public static final String TAG_MAIN_COURSES_LIST = "MainCoursesList";
	public static final String TAG_SOUPS_LIST = "SoupsList";
	public static final String TAG_VEGETERIAN_MEALS_LIST = "VegetarianMealsList";
	public static final String TAG_DESSERTS_LIST = "DessertsList";
	public static final String TAG_TIME_SAVERS_LIST = "TimeSaversList";
	public static final String TAG_FAVORITES_LIST = "FavoritesList";
	
	public static final String TAG_RECIPE_INFO = "RecipeInfo";
	public static final String TAG_RECIPE_INFO_TITLE = "RecipeTitle";
	public static final String TAG_RECIPE_INFO_PICTURE_LIST = "RecipePictureList";
	public static final String TAG_RECIPE_INFO_PICTURE = "RecipePicture";
	public static final String TAG_RECIPE_INFO_SUMMARY = "RecipeSummary";
	public static final String TAG_RECIPE_INFO_SUMMARY_ORIGIN = "RecipeSummaryOrigin";
	public static final String TAG_RECIPE_INFO_SUMMARY_PREPARATION_TIME = "RecipeSummaryPreparationTime";
	public static final String TAG_RECIPE_INFO_SUMMARY_COOKING_TIME = "RecipeSummaryCookingTime";
	public static final String TAG_RECIPE_INFO_SUMMARY_PORTIONS = "RecipeSummaryPortions";
	public static final String TAG_RECIPE_INFO_SUMMARY_CALORIES = "RecipeSummaryCalories";
	public static final String TAG_RECIPE_INFO_SUMMARY_DESCRIPTION = "RecipeSummaryDescription";
	public static final String TAG_RECIPE_INFO_INGREDIENTS_LIST = "RecipeIngredientsList";
	public static final String TAG_RECIPE_INFO_INGREDIENTS_LIST_ITEM = "RecipeIngredient";
	public static final String TAG_RECIPE_INFO_INGREDIENTS_ITEM_NAME = "RecipeIngredientsName";
	public static final String TAG_RECIPE_INFO_INGREDIENTS_ITEM_QUANTITY = "RecipeIngredientsQuantity";
	public static final String TAG_RECIPE_INFO_STEPS_LIST = "RecipeStepsList";
	public static final String TAG_RECIPE_INFO_STEPS_LIST_ITEM = "RecipeStep";
	public static final String TAG_RECIPE_INFO_STEPS_ITEM_NAME = "RecipeStepName";
	public static final String TAG_RECIPE_INFO_STEPS_ITEM_DESCRIPTION = "RecipeStepDescription";
}
