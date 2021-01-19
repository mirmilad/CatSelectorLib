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
| title | CharSequence | The title of CatSelectorActivity actionbar | Empty |
| titleStyleResId | Int | Style resource Id for CatSelectorActivity actionbar tile | 0 |
| backButtonText | CharSequence | The text of CatSelectorActivity actionbar back button | Empty |
| backButtonStyleResId | Int | Style resource Id for CatSelectorActivity actionbar back button | 0 |
| actionbarStyleResId | Int | Style resource Id for CatSelectorActivity actionbar | 0 |
