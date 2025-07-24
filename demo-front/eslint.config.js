// Note the `/flat` suffix here, the difference from default entry is that
// `/flat` added `name` property to the exported object to improve
// [config-inspector](https://eslint.org/blog/2024/04/eslint-config-inspector/) experience.
// 参考URL: https://zenn.dev/kazuwombat/articles/20e0c0cc83c3f2 => 公式URL: https://prettier.io/docs/integrating-with-linters.html
import eslintConfigPrettier from "eslint-config-prettier/flat";
import vuetify from "eslint-config-vuetify";

export default [vuetify, eslintConfigPrettier];
