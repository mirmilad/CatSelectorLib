## Cat Selector Lib
This is a simple android library that allows you browse images and select one image from [TheCatApi](https://thecatapi.com/).

### Project Structure
This project includes two modules named `app` and `catselectorlib`. Module `app` is the sample app that shows how to use this library and module `catselectorlib` is the source code of this library.

### Usage

#### dependencies
Import the `catselectorlib` module to your project or build the `catselectorlib` module and import `catselectorlib.arr` file in to your project.

#### How to use
First register in [TheCatApi](https://thecatapi.com/) website and get your API key.

This library has an activity named `CatSelectorActivity` which shows list of images and allows you to select one of them. In order to start this activity correctly you must pass at least API Key as options. In addition, you can use other options to customize the UI and pass desired options to the API.

You must create an instance of `CatSelectorOptions` class. This class has two properties named `apiOptions` and `uiOptions` that you can set them through constructor. `apiOption` property is mandatory but `uiOption` property is optional.
The following code snippet shows how to crate a simple instance of `CatSelectorOptions` class.
```
val options = CatSelectorOptions(ApiOptions("Your API Key"))
```
Now you must create an `Intent` to start the activity. You can create an instance of `Intent` class and pass options to it manually
```
val intent = Intent(context, CatSelectorActivity::class.java)
intent.putExtra(CatSelectorIntent.OPTIONS_PARAM, options)
```
or you can use `CatSelectorIntent` class to make it simple
```
val intent = CatSelectorIntent(context, options)
```
Now you have the intent and you must start activity by calling `startActivityForResult` method.
```
startActivityForResult(intent, YOUR_REQUEST_CODE)
```
Then you must override the `onActivityResult` method in your activity. When user returns from `CatSelectorActivity`, this overrided method will be called. Then you should check the `requestCode` parameter first and if it's matched with your requestCode, then you must check the `resultCode` parameter to check if the result is `RESULT_OK` or not. In case of `RESULT_OK` you can access to the image Uri by calling `getData` method of the intent.
```
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
	super.onActivityResult(requestCode, resultCode, data)
	if(requestCode == REQUEST_CODE) {
		if(resultCode == RESULT_OK) {
			val imageUri = data?.data
			
			//Do your job with image here
		}
	}
}
```
When users select an image, this library download the selected image on the cache folder and then return the Uri of downloaded image. it's your duty to delete the image file when you don't need it any more.

##### Advanced Options
As mentioned before, `CatSelectorOptions` class has two properties that allows you customize the library.

The `ApiOptions` class has the following properties that use them to call [Get All Public Images Api](https://docs.thecatapi.com/api-reference/images/images-search).

| Property name | type | Description | Default Value |
|---|---|---|---|
| apiKey | String | x-api-key header from TheCatApi, Your API Key from TheCatApi website | No Default Value |
| size  | String | size query param from TheCatApi, values can be <ul><li>ApiOptions.SIZE_FULL</li><li>ApiOptions.SIZE_MEDIUM</li><li>ApiOptions.SIZE_SMALL</li><li>ApiOptions.SIZE_THUMB</li><li>ApiOptions.SIZE_FULL</li><li>ApiOptions.SIZE_ALL</li></ul> | ApiOptions.SIZE_ALL |
| mimeTypes  | String[] | mime_types query param from TheCatApi | Empty Array |
| order  | String | order query param from TheCatApi, values can be <ul><li>ApiOptions.ORDER_RANDOM</li><li>ApiOptions.ORDER_ASC</li><li>ApiOptions.ORDER_DESC</li></ul> | ApiOptions.ORDER_RANDOM |
| limit  | Int | limit query param from TheCatApi | 50 |
| categoryIds  | Int[] | category_ids query param from TheCatApi | Empty Array |
| breedId  | String | breed_id query param from TheCatApi | Empty String |

The `UIOptions` class has the following properties that allows you to customize `CatSelectorActivity` UI.

| Property name | type | Description | Default Value |
|---|---|---|---|
| title | CharSequence? | The title of CatSelectorActivity actionbar. Note: If you set this value, it will replace the value that you set in the style | null |
| backButtonText | CharSequence? | The text of CatSelectorActivity actionbar back button. Note: If you set this value, it will replace the value that you set in the style | null |
| theme | Int | Theme resource Id for CatSelectorActivity | 0 |

If you like to customize the UI, you must define a new style for `CatSelectorActivity` and pass this style through `theme` property of `UiOptions` class. This style should use `@style/CatSelectorLibTheme` or one of appcompat's [NoActionBar](https://developer.android.com/reference/androidx/appcompat/R.style#Theme_AppCompat_NoActionBar) themes as parent. 
```
<style name="CatSelectorCustomTheme" parent="@style/CatSelectorLibTheme">
	...
</style>
```
or 
```
<style name="CatSelectorCustomTheme" parent="Theme.AppCompat.Light.NoActionBar">
	...
</style>
```
Then you can override the `CatSelectorActivity` theme easily. There are 3 custom attributes that allow you customize actionbar, title and back button easily, as below:
```
<style name="CatSelectorCustomTheme" parent="@style/CatSelectorLibTheme">

	<item name="catSelectorLibActionBarStyle">...</item>
	<item name="catSelectorLibActionBarTitleStyle">...</item>
	<item name="catSelectorLibActionBarBackButtonStyle">...</item>
	
</style>
```
Important: if you don't use `@style/CatSelectorLibTheme` as parent of your theme, you must provide `catSelectorLibActionBarTitleStyle` and `catSelectorLibActionBarBackButtonStyle` attributes. You can extend the default styles (`catSelectorLibDefaultActionBarTitleStyle`, `catSelectorLibDefaultActionBarBackButtonStyle`) and override them or create a new style. If you have decided to create a new style for title or back button, you must provide both `android:layout_width` and `android:layout_height` values for your styles.

Also, you can set the title or back button text programatically. It will override the default text or the custom text that you have provided in your custom style.
```
val uiOptions = UIOptions("Custom Title", "Custom Back", R.style.CatSelectorCustomTheme)
```

##### Startup configuration
This library uses [App Startup library](https://developer.android.com/topic/libraries/app-startup) for first initialization. If you are using this library in your project, use `tools:node="merge"` attribute on `provider` tag. For more information please read the [official documentation](https://developer.android.com/topic/libraries/app-startup).

##### Databinding configuration
This library uses [Databinding library](https://developer.android.com/jetpack/androidx/releases/databinding). Please make sure you have enabled data biding in your project. To enable data binding, set the dataBinding build option to true in your module's build.gradle file, as shown below:
```
dataBinding {
    enabled true
}
```

### More Information
For more information and samples, please look at the sample project source code.
