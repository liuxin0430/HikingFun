# HikingFun
An Android App for Hiking lovers, providing some useful functions during trip

Now it only contains a basic content. The whole app is divided into four part: GPS, Step, Note and More.
GPS provides the location information. Step is targeted for step number count. Note is used for text recording. And the More part contains some useful features, like flash light and power-saving.

## Basic Architecture
I use four Fragments as the containers instead of Activity, because Fragment can be considered as mini Activity. It's convenient to combine several fragments in a single Activity.

In order to manage those four fragments, I create a class named MyFragmentPagerAdapter extended from FragmentPagerAdapter.

What’s more, I use four RadioButtons, which are grouped by RadioGroup, as the navigation bar under the bottom of screen. It can help to manage and switch between four functions better.
