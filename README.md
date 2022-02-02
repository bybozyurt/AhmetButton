# Ahmet Button

- Shadow Effect
- Shadow Color
- Shadow Color Brightness
- Shadow Height
- Shadow Enabled
- Ripple Effect
- Ripple Color
- Ripple Opacity
- Corner Radius
- Text Color
- Background Color


## Installation

**build.gradle** in project module

```
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    ...
```

**setings.gradle**

```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
        jcenter()
    }
}
...
...
```

**build.gradle** in app module


```
implementation 'com.github.bybozyurt:AhmetButton:v1.2'
```

## Usage

XML

```
	
 <com.example.custombutton.AhmetButton
        android:id="@+id/ahmetButton"
        android:layout_width="360dp"
        android:layout_height="75dp"
        android:stateListAnimator="@null"
        android:text="AB"
        android:enabled="true"
        app:ab_shadowEnabled="true"
        app:ab_useRippleEffect="true"
        app:ab_backgroundColorNormal="@color/holo_purple"
        app:ab_textColorNormal="@color/colorAccent"
        app:ab_radius="45dp"
        app:ab_rippleColor="@color/holo_red_light"
        app:ab_rippleOpacity="150"
        app:ab_shadowColorBrightness="true"
        app:ab_shadowHeight="8dp"
        />
        
 <com.example.custombutton.AhmetButton
        android:id="@+id/btn"
        android:layout_width="match_parent"
        android:layout_height="60dp"
        app:ab_useRippleEffect="true"
        app:ab_shadowEnabled="true"
        app:ab_shadowHeight="6dp"
        app:ab_shadowColorBrightness="false"
        app:ab_shadowColor="@color/holo_purple"
        app:ab_radius="45dp"
        app:ab_backgroundColorNormal="@color/yellow"
        app:ab_rippleColor="@color/colorPrimaryDark"
        app:ab_rippleOpacity="150"
        android:text="BUTTON"
        />

```

Or programmatically:

```
val btn : AhmetButton = findViewById(R.id.btn)

//with builder
AhmetButtonBuilder(this)
            .backgroundColor(ContextCompat.getColor(applicationContext,R.color.color_orange))
            .shadowColor(ContextCompat.getColor(applicationContext, R.color.holo_red_dark))
            .rippleColor(ContextCompat.getColor(applicationContext, R.color.holo_blue_dark))
            .rippleOpacity(15f)
            .radius(45)
            .shadowHeight(18)
            .textColor(ContextCompat.getColor(applicationContext, R.color.black))
            .build(btn)
          
// OR          
btn.setBtnBackgroundColor(ContextCompat.getColor(applicationContext,R.color.color_orange))
btn.setBtnRadius(45)
btn.setBtnShadowColor(ContextCompat.getColor(applicationContext, R.color.holo_red_dark))
btn.setBtnShadowHeight(18)
btn.setRippleColor(ContextCompat.getColor(applicationContext, R.color.holo_purple))
btn.setRippleOpacity(150)
btn.setTextColor(ContextCompat.getColor(applicationContext, R.color.black))
```




<img src="https://user-images.githubusercontent.com/51344498/152018302-4073df84-38b3-4727-a4c5-ef345af8ed52.gif" width="400" height="800" />
