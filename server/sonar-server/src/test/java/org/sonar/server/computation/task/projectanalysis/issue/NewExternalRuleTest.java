/*
 * SonarQube
 * Copyright (C) 2009-2018 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.server.computation.task.projectanalysis.issue;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.rules.RuleType;

import static org.assertj.core.api.Assertions.assertThat;

public class NewExternalRuleTest {
  @org.junit.Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void should_build_new_external_rule() {
    NewExternalRule.Builder builder = new NewExternalRule.Builder()
      .setKey(RuleKey.of("repo", "rule"))
      .setPluginKey("repo")
      .setName("name")
      .setSeverity("MAJOR")
      .setType(RuleType.BUG);

    assertThat(builder.severity()).isEqualTo("MAJOR");
    assertThat(builder.type()).isEqualTo(RuleType.BUG);
    assertThat(builder.name()).isEqualTo("name");

    NewExternalRule rule = builder.build();

    assertThat(rule.getName()).isEqualTo("name");
    assertThat(rule.getPluginKey()).isEqualTo("repo");
    assertThat(rule.getSeverity()).isEqualTo("MAJOR");
    assertThat(rule.getType()).isEqualTo(RuleType.BUG);
  }

  @Test
  public void fail_if_type_is_not_set() {
    exception.expect(IllegalStateException.class);
    exception.expectMessage("'type' not expected to be null for an external rule");

    new NewExternalRule.Builder()
      .setKey(RuleKey.of("repo", "rule"))
      .setSeverity("MAJOR")
      .build();
  }

  @Test
  public void fail_if_rule_key_is_not_set() {
    exception.expect(IllegalStateException.class);
    exception.expectMessage("'key' not expected to be null for an external rule");

    new NewExternalRule.Builder()
      .setSeverity("MAJOR")
      .setType(RuleType.BUG)
      .build();
  }
}