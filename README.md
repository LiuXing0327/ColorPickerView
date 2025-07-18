# ColorPickerView
一个简单的 Android ColorPickerView

作为一个开源项目，如果它对你有所帮助可以给 star 支持。你的支持对我非常重要！

## Screenshot
<div>
<img src="Screenshot/Screenshot_20250715_101756.png" width="100%"/>
</div>

## Dependency
Add it in your root settings.gradle at the end of repositories
```
	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url = uri("https://jitpack.io") }
		}
	}
```
 Add the dependency
```
	dependencies {
	        implementation("com.github.LiuXing0327:ColorPickerView:V1.0")
	}
```

## Usage
```
    <com.liuxing.library.ColorPickerView
        android:layout_width="match_parent"
        android:layout_height="200dp"/>
```
```
        val colorPickerView =
            findViewById<ColorPickerView>(R.id.color_picker_view)
        colorPickerView.onColorChanged =
            { color -> main.setBackgroundColor(color) }
```

## License
```
 Copyright [2025] [LiuXing]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
